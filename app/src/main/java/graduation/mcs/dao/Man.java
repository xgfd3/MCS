package graduation.mcs.dao;

import graduation.mcs.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "conference_special_men".
 */
public class Man {

    private Long id;
    private String account_phone;
    private String conference_uuid;
    private long conference_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ManDao myDao;

    private Conference conference;
    private Long conference__resolvedKey;


    public Man() {
    }

    public Man(Long id) {
        this.id = id;
    }

    public Man(Long id, String account_phone, String conference_uuid, long conference_id) {
        this.id = id;
        this.account_phone = account_phone;
        this.conference_uuid = conference_uuid;
        this.conference_id = conference_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getManDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount_phone() {
        return account_phone;
    }

    public void setAccount_phone(String account_phone) {
        this.account_phone = account_phone;
    }

    public String getConference_uuid() {
        return conference_uuid;
    }

    public void setConference_uuid(String conference_uuid) {
        this.conference_uuid = conference_uuid;
    }

    public long getConference_id() {
        return conference_id;
    }

    public void setConference_id(long conference_id) {
        this.conference_id = conference_id;
    }

    /** To-one relationship, resolved on first access. */
    public Conference getConference() {
        long __key = this.conference_id;
        if (conference__resolvedKey == null || !conference__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConferenceDao targetDao = daoSession.getConferenceDao();
            Conference conferenceNew = targetDao.load(__key);
            synchronized (this) {
                conference = conferenceNew;
            	conference__resolvedKey = __key;
            }
        }
        return conference;
    }

    public void setConference(Conference conference) {
        if (conference == null) {
            throw new DaoException("To-one property 'conference_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.conference = conference;
            conference_id = conference.getId();
            conference__resolvedKey = conference_id;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
