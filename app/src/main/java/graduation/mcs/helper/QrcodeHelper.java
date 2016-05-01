package graduation.mcs.helper;

import android.view.ViewTreeObserver;
import android.widget.ImageView;
import graduation.mcs.dao.Attendance;
import graduation.mcs.dao.Conference;
import graduation.mcs.dao.ConferenceSigned;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.widget.qrcode.CaptureActivity;
import graduation.mcs.widget.qrcode.Qrcode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xucz on 2016/4/20.
 */
public class QrcodeHelper {

  private final Qrcode qrcode;

  private QrcodeHelper() {
    qrcode = Qrcode.getInstance(FileHelper.getInstance().getQrcodeCachePath());
  }

  public void display(final String qrcodeJson, final ImageView qrcodeView) {
    qrcodeView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
              @Override public void onGlobalLayout() {
                LogUtils.e(this, "qrcodeView width: " + qrcodeView.getWidth());
                qrcode.encodeAnddisplay(qrcodeJson, qrcodeView, qrcodeView.getWidth());
                qrcodeView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
              }
            });
  }

  public void decode(String path, CaptureActivity captureActivity) {
    qrcode.decodeImageUrl(path, captureActivity);
  }

  public static class SingleTon {
    public static QrcodeHelper qrcodeHelper = new QrcodeHelper();
  }

  public static QrcodeHelper getInstance() {
    return SingleTon.qrcodeHelper;
  }

  /*
  二维码信息
  {"from":"15521108281","conference_uuid":""}
   */
  public String getQrcodeJson(String from, String conferenceUUID) {
    JSONObject root = new JSONObject();
    try {
      root.put("from", from);
      root.put("conference_uuid", conferenceUUID);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return root.toString();
  }

  public String[] parseQrcodeJson(String json) {
    String [] data = new String[2];
    try {
      JSONObject root = new JSONObject(json);
      data [0] = root.getString("from");
      data [1]= root.getString("conference_uuid");
      return data;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
  签到信息，当得到二维码信息后发送
  subject:1
   {"conference_uuid":"","account_phone":"", "account_nickname":"",
   "time_sign":"", "place_sign":""}
   */
  public String getSignInfo(Attendance attendance) {
    JSONObject root = new JSONObject();
    try {
      root.put("conference_uuid", attendance.getConference_uuid());
      root.put("account_phone", attendance.getAccount_phone());
      root.put("account_nickname", attendance.getAccount_nickname());
      root.put("time_sign", attendance.getTime_sign());
      root.put("place_sign", attendance.getPlace_sign());
      return root.toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Attendance parseSignInfo(String json) {
    Attendance attendance = null;
    try {
      attendance = new Attendance();
      JSONObject root = new JSONObject(json);
      attendance.setConference_uuid(root.getString("conference_uuid"));
      attendance.setAccount_phone(root.getString("account_phone"));
      attendance.setAccount_nickname(root.getString("account_nickname"));
      attendance.setTime_sign(root.getLong("time_sign"));
      attendance.setPlace_sign(root.getString("place_sign"));
      return attendance;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
  会议信息，当收到签到信息并验证通过时发送会会议信息
  subject:2
  {"creator_account_phone":"","conference_uuid":"", "conference_img":"","conference_theme":"",
  "conference_des":"","conference_place":"", "conference_time_create":"",
  "conference_time_start":"", "conference_time_end":""}
   */
  public String getConferenceJson(Conference conference) {
    JSONObject root = new JSONObject();
    try {
      root.put("creator_account_phone", conference.getCreator_account_phone());
      root.put("conference_uuid", conference.getConference_uuid());
      root.put("conference_img", conference.getIcon_img());
      root.put("conference_theme", conference.getTheme());
      root.put("conference_des", conference.getDescription());
      root.put("conference_place", conference.getPlace());
      root.put("conference_time_create", conference.getTime_create());
      root.put("conference_time_start", conference.getTime_begin());
      root.put("conference_time_end", conference.getTime_end());
      return root.toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public ConferenceSigned parseConferenceJson(String json) {
    ConferenceSigned conference = new ConferenceSigned();
    try {
      JSONObject root = new JSONObject(json);
      conference.setCreator_account_phone(root.getString("creator_account_phone"));
      conference.setConference_uuid(root.getString("conference_uuid"));
      //conference.setIcon_img((byte[]) root.get("conference_img"));
      conference.setTheme(root.getString("conference_theme"));
      conference.setDescription(root.getString("conference_des"));
      conference.setPlace(root.getString("conference_place"));
      conference.setTime_create(root.getLong("conference_time_create"));
      conference.setTime_begin(root.getLong("conference_time_start"));
      conference.setTime_end(root.getLong("conference_time_end"));
      return conference;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
  错误信息，当收到签到信息并验证失败时发送错误信息
  subject:3
  {"conference_uuid":"","error_msg":""}
   */
  public String getErrorJson(String conferenceUUID, String errorMsg) {
    JSONObject root = new JSONObject();
    try {
      root.put("conference_uuid", conferenceUUID);
      root.put("error_msg", errorMsg);
      return root.toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String[] parseErrorJson(String json) {
    String[] data = new String[2];
    try {
      JSONObject root = new JSONObject(json);
      data[0] = root.getString("conference_uuid");
      data[1] = root.getString("error_msg");
      return data;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
