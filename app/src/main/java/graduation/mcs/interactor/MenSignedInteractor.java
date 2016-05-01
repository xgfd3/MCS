package graduation.mcs.interactor;

import graduation.mcs.dao.Attendance;
import graduation.mcs.dao.AttendanceDao;
import graduation.mcs.helper.DaoHelper;
import graduation.mcs.widget.executor.JobExecutor;
import graduation.mcs.widget.executor.UIExecutor;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xucz on 2016/4/23.
 */
public class MenSignedInteractor {

  private final JobExecutor jobExecutor;
  private final UIExecutor uiExecutor;
  private final AttendanceDao attendanceDao;

  public MenSignedInteractor(){
    jobExecutor = JobExecutor.getInstance();
    uiExecutor = UIExecutor.getInstance();
    attendanceDao = DaoHelper.getInstance().getDaoSession().getAttendanceDao();
  }

  public void loadMenSigned(String uuid, Subscriber<List<Attendance>> subscriber) {
    buildMenSignedLoader(uuid).subscribe(subscriber);

  }

  private Observable buildMenSignedLoader(final String uuid) {
    return build(new Observable.OnSubscribe<List<Attendance>>() {
      @Override public void call(Subscriber<? super List<Attendance>> subscriber) {
        List<Attendance> attendances = attendanceDao.queryBuilder()
            .where(AttendanceDao.Properties.Conference_uuid.eq(uuid))
            .list();
        subscriber.onNext(attendances);
        subscriber.onCompleted();
      }
    });
  }

  private Observable build(Observable.OnSubscribe onSubscribe) {
    return Observable.create(onSubscribe)
        .subscribeOn(Schedulers.from(jobExecutor))
        .observeOn(uiExecutor.getScheduler());
  }
}
