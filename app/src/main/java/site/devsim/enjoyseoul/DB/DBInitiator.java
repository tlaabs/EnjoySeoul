package site.devsim.enjoyseoul.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.UnsupportedCharsetException;

import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.NumericUtil;

public class DBInitiator {
    private static final String TAG = "DBInitiator";
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;

    private static final int IS_FREE_UNKNOWN = -1;


    private Context context;
    private JobFinishListener jobFinishListener;

    private DBManager db;

    public DBInitiator(Context context, JobFinishListener jobFinishListener) {
        this.context = context;
        this.jobFinishListener = jobFinishListener;
        this.db = DBManager.getInstance(context);
    }

    public void init() {
        DownloadEventDataTask task1 = new DownloadEventDataTask();
        task1.execute();
    }

    private class DownloadEventDataTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                FileOutputStream fileOutput = null;
                InputStream inputStream = null;

                try {
                    URL url = new URL(context.getString(R.string.data_src_url));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(false);

                    File cachePath = context.getCacheDir();
                    File file = new File(cachePath, context.getString(R.string.data_name));
                    fileOutput = new FileOutputStream(file);
                    inputStream = conn.getInputStream();

                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;

                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                    }
                } catch (IOException e) {
                    return FAIL;
                } finally {
                    if (fileOutput != null) {
                        fileOutput.close();
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                return FAIL;
            }
            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer state) {
            if (state == SUCCESS) {
                InputDataToDBTask task2 = new InputDataToDBTask();
                task2.execute();
            } else if (state == FAIL) {
                Toast.makeText(context, context.getString(R.string.data_download_err), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InputDataToDBTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                ContentValues recordValues;
                CSVReader reader = null;
                try {
                    File file = new File(context.getCacheDir(), context.getString(R.string.data_name));
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

                    reader = new CSVReader(isr);
                    String[] nextLine = null;
                    reader.readNext();
                    db.beginTransaction();
                    while ((nextLine = reader.readNext()) != null) {
                        recordValues = new ContentValues();
                        recordValues.put("ID", Integer.parseInt(nextLine[0]));
                        recordValues.put("GENRE", nextLine[2]);
                        recordValues.put("TITLE", nextLine[3]);
                        recordValues.put("START_DATE", nextLine[4]);
                        recordValues.put("END_DATE", nextLine[5]);
                        recordValues.put("TIME", nextLine[6]);
                        recordValues.put("PLACE", nextLine[7]);
                        recordValues.put("ORG_LINK", nextLine[8]);
                        recordValues.put("MAIN_IMG", nextLine[9]);
                        recordValues.put("TARGET", nextLine[11]);
                        recordValues.put("FEE", nextLine[12]);
                        recordValues.put("INQUIRY", nextLine[14]);
                        recordValues.put("ETC_DESC", nextLine[16]);

                        //불분명한 IS_FREE 값 처리
                        String isFree = nextLine[18];
                        if (!(isFree.equals("")) && NumericUtil.isNumeric(isFree)) {
                            recordValues.put("IS_FREE", Integer.parseInt(nextLine[18]));
                        } else {
                            recordValues.put("IS_FREE", IS_FREE_UNKNOWN);
                        }

                        db.insert(context.getString(R.string.event_table), null, recordValues);
                    }
                    db.setTransactionSuccessful();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return FAIL;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return FAIL;
                } catch (IOException e) {
                    e.printStackTrace();
                    return FAIL;
                } finally {
                    db.endTransaction();
                    if (reader != null) {
                        reader.close();
                    }
                }
            }catch(IOException e){
                return FAIL;
            }

            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer state) {
            if (state == SUCCESS) {
                jobFinishListener.doJob();
            } else if (state == FAIL) {
                Toast.makeText(context, context.getString(R.string.data_record_err), Toast.LENGTH_LONG).show();
            }
        }
    }
}
