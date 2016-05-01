package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MCSDaoGenerator {

  public static void main(String[] arg) throws Exception {
    Schema schema = new Schema(1000, "graduation.mcs.dao");

    createTable(schema);


    new DaoGenerator().generateAll(schema, "E:\\College\\GraduationProject\\Project\\MCS\\app\\src\\main\\java");
  }

  private static void createTable(Schema schema) {
    // 用户表
    /*
      id
      nickname -- vcard.nickname
      phone
      password_plain
      password_encrypted
      real_name -- vcard.firstname: 有了真名才算完善了
      sax -- vcard.middlename
      birthday ： Unix时间截
      job -- vcard.job
      work_place  -- vcard.organization
      head_img : 图片字节流
      status  : -1:不存在,0未激活,1激活未完善,2激活已完善
     */
    Entity account = schema.addEntity("Account");
    account.setTableName("account");
    account.addIdProperty();
    account.addStringProperty("nickname").notNull();
    account.addStringProperty("phone").notNull();
    account.addStringProperty("password_plain");
    account.addStringProperty("password_encrypted");
    account.addStringProperty("real_name");
    account.addStringProperty("sax");
    account.addLongProperty("birthday");
    account.addStringProperty("job");
    account.addStringProperty("work_place");
    account.addByteArrayProperty("head_img");
    account.addIntProperty("status");

    // 会议表
    /*
      id
      creator_account_phone  : 创建者的手机号
      time_create : Unix时间截
      time_begin : Unix时间截
      time_end : Unix时间截
      theme
      description
      place
      icon_img: 图标字节流 -
      attendance_size -
      open_status: 0低;1中;2高 -
      uuid: 会议的唯一标识
     */
    Entity conference = schema.addEntity("Conference");
    conference.setTableName("conference");
    Property conference_id = conference.addIdProperty().getProperty();
    Property conference_uuid = conference.addStringProperty("conference_uuid").notNull().getProperty();
    conference.addStringProperty("creator_account_phone").notNull();
    conference.addLongProperty("time_create").notNull();
    conference.addLongProperty("time_begin").notNull();
    conference.addLongProperty("time_end").notNull();
    conference.addStringProperty("theme").notNull();
    conference.addStringProperty("description");
    conference.addStringProperty("place").notNull();
    conference.addIntProperty("attendance_size");
    conference.addIntProperty("open_status");
    conference.addByteArrayProperty("icon_img");

    // 指定会议成员表conference_people
    /*
      id
      conference_id: 外键
      conference_uuid
      account_phone
     */
    Entity man = schema.addEntity("Man");
    man.setTableName("conference_special_men");
    man.addIdProperty();
    Property account_phone = man.addStringProperty("account_phone").getProperty();
    Property conference_uuid_p = man.addStringProperty("conference_uuid").getProperty();
    Property conference_id_p = man.addLongProperty("conference_id").notNull().getProperty();
    man.addToOne(conference, conference_id_p);

    ToMany conferenceToPeople = conference.addToMany(man, conference_id_p);
    conferenceToPeople.setName("men");
    conferenceToPeople.orderAsc(account_phone);

    // 签到表
    /*
      id
      conference_uuid
      sign_phone : 签到者的账号手机号
      sign_nickname: 签到者昵称
      time_sign : 签到时间
      place_sign  : 签到地点
      pp_account_phone 代签者的账号手机号，代替签到者的用户的Jid
     */
    Entity attendance = schema.addEntity("Attendance");
    attendance.setTableName("attendance");
    attendance.addIdProperty();
    Property conference_uuid_a = attendance.addStringProperty("conference_uuid").notNull().getProperty();
    Property account_phone1 = attendance.addStringProperty("account_phone").notNull().getProperty();
    Property account_nickname = attendance.addStringProperty("account_nickname").notNull().getProperty();
    Property time_sign = attendance.addLongProperty("time_sign").getProperty();
    attendance.addStringProperty("place_sign");
    //attendance.addStringProperty("pp_account_phone");// 去掉代签情况

    // 签到过的会议表
    /*
      id
      creator_account_phone
      time_create : Unix时间截
      time_begin : Unix时间截
      time_end : Unix时间截
      theme
      description
      place
      icon_img: 图标字节流 -
      uuid: 会议的唯一标识
      sign_account_phone
     */
    Entity conferenceSigned = schema.addEntity("ConferenceSigned");
    conferenceSigned.setTableName("conference_signed");
    Property conference_sign__id = conferenceSigned.addIdProperty().getProperty();
    Property conference_sign_uuid = conferenceSigned.addStringProperty("conference_uuid").notNull().getProperty();
    conferenceSigned.addStringProperty("creator_account_phone").notNull();
    conferenceSigned.addStringProperty("sign_account_phone").notNull();
    conferenceSigned.addLongProperty("time_create").notNull();
    conferenceSigned.addLongProperty("time_begin").notNull();
    conferenceSigned.addLongProperty("time_end").notNull();
    conferenceSigned.addStringProperty("theme").notNull();
    conferenceSigned.addStringProperty("description");
    conferenceSigned.addStringProperty("place").notNull();
    conferenceSigned.addByteArrayProperty("icon_img");

  }
}
