package graduation.mcs.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "conference_signed".
 */
public class ConferenceSigned {

    private Long id;
    /** Not-null value. */
    private String conference_uuid;
    /** Not-null value. */
    private String creator_account_phone;
    /** Not-null value. */
    private String sign_account_phone;
    private long time_create;
    private long time_begin;
    private long time_end;
    /** Not-null value. */
    private String theme;
    private String description;
    /** Not-null value. */
    private String place;
    private byte[] icon_img;

    public ConferenceSigned() {
    }

    public ConferenceSigned(Long id) {
        this.id = id;
    }

    public ConferenceSigned(Long id, String conference_uuid, String creator_account_phone, String sign_account_phone, long time_create, long time_begin, long time_end, String theme, String description, String place, byte[] icon_img) {
        this.id = id;
        this.conference_uuid = conference_uuid;
        this.creator_account_phone = creator_account_phone;
        this.sign_account_phone = sign_account_phone;
        this.time_create = time_create;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.theme = theme;
        this.description = description;
        this.place = place;
        this.icon_img = icon_img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getConference_uuid() {
        return conference_uuid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setConference_uuid(String conference_uuid) {
        this.conference_uuid = conference_uuid;
    }

    /** Not-null value. */
    public String getCreator_account_phone() {
        return creator_account_phone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreator_account_phone(String creator_account_phone) {
        this.creator_account_phone = creator_account_phone;
    }

    /** Not-null value. */
    public String getSign_account_phone() {
        return sign_account_phone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSign_account_phone(String sign_account_phone) {
        this.sign_account_phone = sign_account_phone;
    }

    public long getTime_create() {
        return time_create;
    }

    public void setTime_create(long time_create) {
        this.time_create = time_create;
    }

    public long getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(long time_begin) {
        this.time_begin = time_begin;
    }

    public long getTime_end() {
        return time_end;
    }

    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }

    /** Not-null value. */
    public String getTheme() {
        return theme;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Not-null value. */
    public String getPlace() {
        return place;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPlace(String place) {
        this.place = place;
    }

    public byte[] getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(byte[] icon_img) {
        this.icon_img = icon_img;
    }

}
