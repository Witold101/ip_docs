package by.vistar.db;

public class DbInitTables {

    public static final String TABLE_NAME_GOODS = "goods";
    public static final String TABLE_NAME_PARTYS = "partys";
    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_LIST_BAY_SEL = "list_bay_sell";
    public static final String TABLE_NAME_FIRMS = "firms";
    public static final String TABLE_NAME_BANKS = "banks";
    public static final String TABLE_NAME_CURR_CODE = "curr_code";
    public static final String TABLE_NAME_ACC = "accounts";
    public static final String TABLE_NAME_BANK_DOC = "bank_documents";

    public static final Integer MAX_NAME = 25;
    public static final Integer MAX_FULL_NAME = 100;
    public static final Integer MAX_NAME_FIRM = 50;
    public static final Integer MAX_SQR = 25;
    public static final Integer MAX_SWIFT = 25;
    public static final Integer MAX_NUMBER = 25;
    public static final Integer MAX_VAT_NUMBER = 50;
    public static final Integer MAX_ACC_NUMBER = 45;
    public static final Integer MAX_NOTE = 100;
    public static final Integer MAX_CODE = 3;
    public static final Integer MAX_DATETIME = 19;

    //-------------------------------- INIT TABLES ---------------------------------------------------------------
    public static final String MYSQL_INIT_GOODS_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_GOODS+
            "` (`id` INT(11) NOT NULL AUTO_INCREMENT, `name` VARCHAR("+MAX_NAME+") NOT NULL, `full_name` " +
            "VARCHAR("+MAX_FULL_NAME+") NULL DEFAULT NULL, `sqr` VARCHAR("+MAX_SQR+") NOT NULL,`party` " +
            "INT(11) NOT NULL, `note` VARCHAR("+MAX_NOTE+") NULL DEFAULT NULL, PRIMARY KEY (`id`)," +
            " CONSTRAINT `good_party` FOREIGN KEY (`party`) " +
            "REFERENCES `"+TABLE_NAME_PARTYS+"` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION) " +
            "ENGINE = InnoDB  DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_PARTYS_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_PARTYS+
            "` (`id` INT(11) NOT NULL AUTO_INCREMENT,`name` VARCHAR("+MAX_NAME+") NOT NULL,`note` " +
            "VARCHAR("+MAX_NOTE+") NULL DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT " +
            "CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_LIST_BAY_SEL_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_LIST_BAY_SEL+
            "` (`id` INT(11) NOT NULL AUTO_INCREMENT,`number` VARCHAR("+MAX_NUMBER+") NOT NULL DEFAULT '000'," +
            "`date` DATETIME NOT NULL,`good` INT(11) NOT NULL,`quantity` FLOAT NOT NULL, `sum` INT(11) NOT NULL," +
            "`bay` TINYINT(4) NOT NULL, PRIMARY KEY (`id`), " +
            " CONSTRAINT `list_bay_sell_good` FOREIGN KEY (`good`) REFERENCES `"+TABLE_NAME_GOODS+"` (`id`)" +
            " ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_USERS+
            "` (" +
            "`id` INT(11) NOT NULL AUTO_INCREMENT," +
            "`name` VARCHAR("+MAX_NAME+") NOT NULL," +
            "`full_name` VARCHAR("+MAX_FULL_NAME+") NULL DEFAULT NULL, " +
            "`id_firm` INT(11) NOT NULL DEFAULT 0, "+
            "PRIMARY KEY (`id`)," +
            "INDEX (`id_firm`),"+
            "CONSTRAINT `firm_user` FOREIGN KEY (`id_firm`)"+
            "REFERENCES `"+TABLE_NAME_FIRMS+"` (`id`) ON DELETE NO ACTION"+
            " ON UPDATE NO ACTION) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
//----------------------------------------------------------------------------------------------------------------
//---------------------------------------- INIT TABLES BANK ------------------------------------------------------

    public static final String MYSQL_INIT_FIRMS_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_FIRMS+
            "` ("+
            "`id` INT(11) NOT NULL AUTO_INCREMENT,"+
            "`name_firm` VARCHAR("+MAX_NAME_FIRM+") NOT NULL,"+
            "`full_name_firm` VARCHAR("+MAX_FULL_NAME+") NOT NULL,"+
            "`vat_number` VARCHAR("+MAX_VAT_NUMBER+") NULL DEFAULT NULL,"+
            "`main` TINYINT(4) NULL DEFAULT 0,"+
            " PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_BANKS_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_BANKS+
            "` ("+
            "`id` INT(11) NOT NULL AUTO_INCREMENT,"+
            "`bic` VARCHAR("+MAX_SWIFT+") NOT NULL,"+
            "`name_bank` VARCHAR("+MAX_FULL_NAME+") NOT NULL,"+
            "`address_id` INT(11) NOT NULL DEFAULT 0,"+
            " PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_CURR_CODE_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_CURR_CODE+
            "` ("+
            "`id` INT(11) NOT NULL AUTO_INCREMENT,"+
            "`code_iso4217` VARCHAR("+MAX_CODE+") NOT NULL,"+
            "`code_iso4217_numb` VARCHAR("+MAX_CODE+") NOT NULL,"+
            "`curr_code_info` VARCHAR("+MAX_NOTE+") NULL DEFAULT NULL,"+
            " PRIMARY KEY (`id`), UNIQUE (`code_iso4217_numb`))" +
            " ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_ACC_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_ACC+
            "` ("+
            "`id` INT(11) NOT NULL AUTO_INCREMENT,"+
            "`number` VARCHAR("+MAX_ACC_NUMBER+") NOT NULL,"+
            "`curr_id` INT(11) NOT NULL,"+
            "`bank_id` INT(11) NOT NULL,"+
            "`firm_id` INT(11) NOT NULL,"+
            " PRIMARY KEY (`id`)," +
            " INDEX (`curr_id`, `bank_id`, `firm_id`),"+
            " CONSTRAINT `curr_acc` FOREIGN KEY (`curr_id`) REFERENCES `"+TABLE_NAME_CURR_CODE+"` (`id`) " +
            "ON DELETE NO ACTION ON UPDATE CASCADE, CONSTRAINT `bank_acc` FOREIGN KEY (`bank_id`) " +
            "REFERENCES `"+TABLE_NAME_BANKS+"` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE, CONSTRAINT " +
            "`firm_acc` FOREIGN KEY (`firm_id`) REFERENCES `"+TABLE_NAME_FIRMS+"` (`id`) ON DELETE NO ACTION"+
            " ON UPDATE CASCADE) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";

    public static final String MYSQL_INIT_BANK_DOC_TABLE = "CREATE TABLE IF NOT EXISTS `"+TABLE_NAME_BANK_DOC+
            "` ("+
            "`id` INT(11) NOT NULL AUTO_INCREMENT,"+
            "`date` DATETIME NOT NULL,"+
            "`number` VARCHAR("+MAX_NUMBER+") NOT NULL,"+
            "`curr_id` INT(11) NOT NULL,"+
            "`payer_id` INT(11) NOT NULL,"+
            "`payer_acc_id` INT(11) NOT NULL,"+
            "`sum` INT(11) NOT NULL DEFAULT 0,"+
            "`info` VARCHAR("+MAX_NOTE+") NOT NULL,"+
            "`debet_doc` TINYINT(4) NULL DEFAULT 1,"+
            " PRIMARY KEY (`id`)," +
            " INDEX (`payer_id`,`curr_id`,`payer_acc_id`), CONSTRAINT `payer_doc` FOREIGN KEY (`payer_id`)"+
            " REFERENCES `"+TABLE_NAME_FIRMS+"` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE," +
            " CONSTRAINT `curr_doc` FOREIGN KEY (`curr_id`) REFERENCES `"+TABLE_NAME_CURR_CODE+"` (`id`) " +
            " ON DELETE NO ACTION ON UPDATE CASCADE, CONSTRAINT `acc_doc` FOREIGN KEY (`payer_acc_id`) " +
            " REFERENCES `"+TABLE_NAME_ACC+"` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE) " +
            " ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
//----------------------------------------------------------------------------------------------------------------
}
