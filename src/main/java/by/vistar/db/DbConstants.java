package by.vistar.db;

import static by.vistar.db.DbInitTables.*;

public class DbConstants {

// ------------------------------------- PARTYS TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_PARTY = "INSERT INTO " + TABLE_NAME_PARTYS +
            " (`name`,`note`) VALUE (?,?)";
    public static final String MYSQL_GET_PARTY = "SELECT * FROM " + TABLE_NAME_PARTYS + " WHERE id=?;";
    public static final String MYSQL_DELL_PARTY = "DELETE FROM " + TABLE_NAME_PARTYS + " WHERE id=?;";
    public static final String MYSQL_EDIT_PARTY = "UPDATE " + TABLE_NAME_PARTYS +
            " SET name=?, note=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- GOODS TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_GOOD = "INSERT INTO " + TABLE_NAME_GOODS +
            " (`name`,`full_name`,`sqr`,`party`,`note`) VALUE (?,?,?,?,?)";
    public static final String MYSQL_GET_GOOD = "SELECT * FROM " + TABLE_NAME_GOODS + " WHERE id=?;";
    public static final String MYSQL_DELL_GOOD = "DELETE FROM " + TABLE_NAME_GOODS + " WHERE id=?;";
    public static final String MYSQL_EDIT_GOOD = "UPDATE " + TABLE_NAME_GOODS +
            " SET name=?, full_name=?, sqr=?, party=?, note=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- LIST BAY SELL TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_LIST = "INSERT INTO " + TABLE_NAME_LIST_BAY_SEL +
            " (`number`,`date`,`good`,`quantity`, `sum`, `bay`) VALUE (?,?,?,?,?,?)";
    public static final String MYSQL_GET_LIST = "SELECT * FROM " + TABLE_NAME_LIST_BAY_SEL + " WHERE id=?;";
    public static final String MYSQL_DELL_LIST = "DELETE FROM " + TABLE_NAME_LIST_BAY_SEL + " WHERE id=?;";
    public static final String MYSQL_EDIT_LIST = "UPDATE " + TABLE_NAME_LIST_BAY_SEL +
            " SET number=?, date=?, good=?, quantity=? sum=? bay=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- USER TABLE ----------------------------------------------------------
public static final String MYSQL_ADD_USER = "INSERT INTO " + TABLE_NAME_USERS +
        " (`name`,`full_name`, `id_firm`) VALUE (?,?,?)";
    public static final String MYSQL_GET_USER = "SELECT * FROM " + TABLE_NAME_USERS + " WHERE id=?;";
    public static final String MYSQL_DELL_USER = "DELETE FROM " + TABLE_NAME_USERS + " WHERE id=?;";
    public static final String MYSQL_EDIT_USER = "UPDATE " + TABLE_NAME_USERS +
            " SET name=?, full_name=?, id_firm=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- FIRMS TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_FIRM = "INSERT INTO " + TABLE_NAME_FIRMS +
            " (`name_firm`,`full_name_firm`,`vat_number`, `main`) VALUE (?,?,?,?)";
    public static final String MYSQL_GET_FIRM = "SELECT * FROM " + TABLE_NAME_FIRMS + " WHERE id=?;";
    public static final String MYSQL_DELL_FIRM = "DELETE FROM " + TABLE_NAME_FIRMS + " WHERE id=?;";
    public static final String MYSQL_EDIT_FIRM = "UPDATE " + TABLE_NAME_FIRMS +
            " SET name_firm=?, full_name_firm=?, vat_number=?, main=? WHERE id=?;";
    public static final String MYSQL_GET_ID_TRUE_FIRM = "SELECT id FROM " + TABLE_NAME_FIRMS + " WHERE main=1 LIMIT 1;";
    public static final String MYSQL_GET_COUNT_ROW_FIRM = "SELECT COUNT(1) FROM " + TABLE_NAME_FIRMS + ";";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- BANKS TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_BANK = "INSERT INTO " + TABLE_NAME_BANKS +
        " (`bic`,`name_bank`, `address_id`) VALUE (?,?,?)";
    public static final String MYSQL_GET_BANK = "SELECT * FROM " + TABLE_NAME_BANKS + " WHERE id=?;";
    public static final String MYSQL_DELL_BANK = "DELETE FROM " + TABLE_NAME_BANKS + " WHERE id=?;";
    public static final String MYSQL_EDIT_BANK = "UPDATE " + TABLE_NAME_BANKS +
            " SET bic=?, name_bank=?, address_id=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- CURR_CODE TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_CURRENCY = "REPLACE INTO " + TABLE_NAME_CURR_CODE +
        " (`code_iso4217`,`code_iso4217_numb`, `curr_code_info`) VALUE (?,?,?)";
    public static final String MYSQL_GET_CURRENCY = "SELECT * FROM " + TABLE_NAME_CURR_CODE + " WHERE id=?;";
    public static final String MYSQL_DELL_CURRENCY = "DELETE FROM " + TABLE_NAME_CURR_CODE + " WHERE id=?;";
    public static final String MYSQL_EDIT_CURRENCY = "UPDATE " + TABLE_NAME_CURR_CODE +
            " SET code_iso4217=?, code_iso4217_numb=?, curr_code_info=? WHERE id=?;";
    public static final String MYSQL_GET_CURR_ID_WITH_CODE = "SELECT * FROM " + TABLE_NAME_CURR_CODE +
            " WHERE code_iso4217_numb=? LIMIT 1;";
//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- ACCOUNT TABLE ----------------------------------------------------------
    public static final String MYSQL_ADD_ACC = "INSERT INTO " + TABLE_NAME_ACC +
        " (`number`,`curr_id`, `bank_id`,`firm_id`) VALUE (?,?,?,?)";
    public static final String MYSQL_GET_ACC = "SELECT * FROM " + TABLE_NAME_ACC + " WHERE id=?;";
    public static final String MYSQL_DELL_ACC = "DELETE FROM " + TABLE_NAME_ACC + " WHERE id=?;";
    public static final String MYSQL_EDIT_ACC = "UPDATE " + TABLE_NAME_ACC +
            " SET number=?, curr_id=?, bank_id=?, firm_id=? WHERE id=?;";

//----------------------------------------------------------------------------------------------------------------
// ------------------------------------- BANK DOCUMENTS TABLE ----------------------------------------------------
    public static final String MYSQL_ADD_PAY = "INSERT INTO " + TABLE_NAME_BANK_DOC +
        " (`date`,`number`, `curr_id`,`payer_id`,`payer_acc_id`,`sum`,`info`, `debet_doc`) VALUE (?,?,?,?,?,?,?,?)";
    public static final String MYSQL_GET_PAY = "SELECT * FROM " + TABLE_NAME_BANK_DOC+ " WHERE id=?;";
    public static final String MYSQL_DELL_PAY = "DELETE FROM " + TABLE_NAME_BANK_DOC + " WHERE id=?;";
    public static final String MYSQL_EDIT_PAY = "UPDATE " + TABLE_NAME_BANK_DOC +
            " SET date=?, number=?, curr_id=?, payer_id=?, payer_acc_id=?, sum=?, info=?, debet_doc=? WHERE id=?;";
//----------------------------------------------------------------------------------------------------------------

}
