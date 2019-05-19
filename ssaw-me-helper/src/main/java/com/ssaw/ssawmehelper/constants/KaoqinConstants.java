package com.ssaw.ssawmehelper.constants;

/**
 * 考勤常量类
 *
 * @author zichun.yang
 */
@SuppressWarnings("all")
public class KaoqinConstants {

    public static final String J_ORIGINAL_DATA_XML = "{" +
            "'DataSet': {" +
            "'DATA': {" +
            "'K_OVER_K_ID': ''," +
            "'K_OVER_BYZJB': ''," +
            "'K_OVER_ZRJB': ''," +
            "'K_OVER_BSKF': ''," +
            "'K_OVER_K_OVERDATE': ''," +
            "'K_OVER_CONFIRM': ''," +
            "'K_OVER_K_OVERTIME': ''," +
            "'K_OVER_K_OVERRQ': ''," +
            "'K_OVER_SHIFTID': ''," +
            "'K_OVER_A0188': ''," +
            "'K_OVER_A0188S': ''," +
            "'K_OVER_ZLOVER_CHECK': ''," +
            "'K_OVER_OVER_BEGIN': ''," +
            "'K_OVER_BEGIN_CHECK': ''," +
            "'K_OVER_OVER_VALID1': ''," +
            "'K_OVER_OVER_END': ''," +
            "'K_OVER_END_CHECK': ''," +
            "'K_OVER_OVER_VALID2': ''," +
            "'K_OVER_OVER_TIME': ''," +
            "'K_OVER_REST_TIME': ''," +
            "'K_OVER_OVER_TYPE': ''," +
            "'K_OVER_ACTIONEMPLOYEE': ''," +
            "'K_OVER_ACTIONTIME': ''," +
            "'K_OVER_BC': ''," +
            "'K_OVER_SIGNED': ''," +
            "'K_OVER_CARD_BEGIN': ''," +
            "'K_OVER_CARD_END': ''," +
            "'K_OVER___CHK': ''," +
            "'K_OVER_JBYY': ''," +
            "'K_OVER_OVER_TIME_FACT': ''," +
            "'K_OVER_OVER_MEMO': ''," +
            "'K_OVER_OVER_SOURCE': ''," +
            "'K_OVER_K_OVER01': ''," +
            "'DataRightType': '0'," +
            "'ValidateState': '1'," +
            "'FormulaState': '1'," +
            "'SequenceID': '0'," +
            "'@RowState': 'Added'" +
            "}" +
            "}" +
            "}";

    @SuppressWarnings("all")
    public static final String TABLES = "{" +
            "'TableName':'DATA'," +
            "'TableTypeID':27," +
            "'TableOrder':3," +
            "'TableLabel':'加班'," +
            "'ACessable':'111'," +
            "'ACessModule':'010001000000000000000000000000'," +
            "'TableVisible':3," +
            "'ModuleID':'05'," +
            "'TableDes':null," +
            "'Grades':0," +
            "'Presere':null," +
            "'enus':null," +
            "'zhtw':null," +
            "'OtherLanguage':null," +
            "'FieldList':[" +
            "{" +
            "'ColumnID':1496," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'K_ID'," +
            "'ColOrder':0," +
            "'ColType':'INT IDENTITY'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班ID'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':1," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'加班ID'," +
            "'OtherLanguage':'残業ID'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':17339," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'BYZJB'," +
            "'ColOrder':1," +
            "'ColType':'NUMERIC'," +
            "'ColWidth':19," +
            "'ColPrecision':2," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'当月累计加班'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':1," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':23997," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'ZRJB'," +
            "'ColOrder':1," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'昨日加班'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':23998," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'BSKF'," +
            "'ColOrder':1," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'不刷卡吗'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':15402," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'K_OVERDATE'," +
            "'ColOrder':1," +
            "'ColType':'DATETIME'," +
            "'ColWidth':16," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'倒休截止日期'," +
            "'DisplayWidth':0," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':2," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':21329," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'CONFIRM'," +
            "'ColOrder':1," +
            "'ColType':'CHAR'," +
            "'ColWidth':1," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班结果确认'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CODE(BM_TJLB|1)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':15403," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'K_OVERTIME'," +
            "'ColOrder':2," +
            "'ColType':'NUMERIC'," +
            "'ColWidth':19," +
            "'ColPrecision':2," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'可倒休时长'," +
            "'DisplayWidth':0," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':2," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':26340," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'K_OVERRQ'," +
            "'ColOrder':33," +
            "'ColType':'DATETIME'," +
            "'ColWidth':10," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'统计日期'," +
            "'DisplayWidth':100," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':3," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':6849," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'SHIFTID'," +
            "'ColOrder':100," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':'55,A018(09:00-18:00)'," +
            "'DisplayLabel':'班次'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'LOOK(K_SHIFTBASE|ID|SHIFT_NAME|)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':'シフト勤務'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1497," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'A0188'," +
            "'ColOrder':100," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'申请人姓名'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'LOOK(A01|A0188|A0101|)'," +
            "'RelationRule':''," +
            "'ColVisible':2," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'人員姓名'," +
            "'OtherLanguage':'従業員氏名'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':15594," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'A0188S'," +
            "'ColOrder':101," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':100," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班人员'," +
            "'DisplayWidth':100," +
            "'DisplayFormat':''," +
            "'EditFormat':'LOOKMORE(A01|A0188|A0101|)'," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':'请假人员'," +
            "'zhtw':'请假人员'," +
            "'OtherLanguage':'请假人员'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':25325," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'ZLOVER_CHECK'," +
            "'ColOrder':200," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'是否直落加班'," +
            "'DisplayWidth':0," +
            "'DisplayFormat':''," +
            "'EditFormat':'CHECK(直落加班|1|0)'," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':3," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1498," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_BEGIN'," +
            "'ColOrder':200," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班开始时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'加班開始時間'," +
            "'OtherLanguage':'残業開始時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1499," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'BEGIN_CHECK'," +
            "'ColOrder':300," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'开始是否要求刷卡'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CHECK(开始时必须刷卡|1|0)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'開始是否要求刷卡'," +
            "'OtherLanguage':'開始時に打刻が必要か'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1500," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_VALID1'," +
            "'ColOrder':400," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'最早刷卡时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'最早刷卡時間'," +
            "'OtherLanguage':'一番早い出席カードする時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1501," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_END'," +
            "'ColOrder':500," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班结束时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'加班結束時間'," +
            "'OtherLanguage':'残業手当'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1502," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'END_CHECK'," +
            "'ColOrder':600," +
            "'ColType':'INT'," +
            "'ColWidth':4," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'结束是否要求刷卡'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CHECK(结束时必须刷卡|1|0)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'結束是否要求刷卡'," +
            "'OtherLanguage':'終了時に打刻が必要か'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1503," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_VALID2'," +
            "'ColOrder':700," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'最晚刷卡时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'最晚刷卡時間'," +
            "'OtherLanguage':'一番遅い出席カード使う時間零点超えるかどうか'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1504," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_TIME'," +
            "'ColOrder':800," +
            "'ColType':'DECIMAL'," +
            "'ColWidth':9," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'预计加班时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'預計加班時間'," +
            "'OtherLanguage':'予定残業時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':3747," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'REST_TIME'," +
            "'ColOrder':800," +
            "'ColType':'NUMERIC'," +
            "'ColWidth':10," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':'0'," +
            "'DisplayLabel':'休息时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'休息時間'," +
            "'OtherLanguage':'休憩時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1505," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_TYPE'," +
            "'ColOrder':900," +
            "'ColType':'CHAR'," +
            "'ColWidth':2," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班类别'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CODE(BM_OVER|0)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'加班類別'," +
            "'OtherLanguage':'残業類別'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':6847," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'ACTIONEMPLOYEE'," +
            "'ColOrder':900," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':100," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'操作人员'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':2," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':'操作従業員'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':6848," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'ACTIONTIME'," +
            "'ColOrder':1000," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'操作时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':2," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':'操作時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':22911," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'BC'," +
            "'ColOrder':1000," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':4000," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班报餐'," +
            "'DisplayWidth':0," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':3," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1506," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'SIGNED'," +
            "'ColOrder':1000," +
            "'ColType':'CHAR'," +
            "'ColWidth':1," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'审批状态'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CODE(BM_SP|1)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'審核標記'," +
            "'OtherLanguage':'審査マーク'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1507," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'CARD_BEGIN'," +
            "'ColOrder':1100," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'开始刷卡'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'開始刷卡'," +
            "'OtherLanguage':'開始時打刻'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1508," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'CARD_END'," +
            "'ColOrder':1200," +
            "'ColType':'DATETIME'," +
            "'ColWidth':8," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'结束刷卡'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':'yyyy-MM-dd HH:mm:ss'," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'結束刷卡'," +
            "'OtherLanguage':'終了時打刻'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':16947," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'__CHK'," +
            "'ColOrder':1200," +
            "'ColType':'CHAR'," +
            "'ColWidth':1," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':'0'," +
            "'DisplayLabel':'加班结果审批'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CODE(BM_SP|1)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':16948," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'JBYY'," +
            "'ColOrder':1201," +
            "'ColType':'CHAR'," +
            "'ColWidth':40," +
            "'ColPrecision':0," +
            "'ColNull':0," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班原因'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':'CODE(BM_JBYY|1)'," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1509," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_TIME_FACT'," +
            "'ColOrder':1300," +
            "'ColType':'DECIMAL'," +
            "'ColWidth':9," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'实际加班时间'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'實際加班時間'," +
            "'OtherLanguage':'実際残業時間'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':1510," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_MEMO'," +
            "'ColOrder':1400," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':40," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'备注'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':1," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'備注'," +
            "'OtherLanguage':'備考'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':4284," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'OVER_SOURCE'," +
            "'ColOrder':1500," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':50," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'来源'," +
            "'DisplayWidth':80," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':1," +
            "'ColProperty':2," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':'來源'," +
            "'OtherLanguage':'出所'," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}," +
            "{" +
            "'ColumnID':23069," +
            "'TableName':'K_OVER'," +
            "'TableTypeId':0," +
            "'ColName':'K_OVER01'," +
            "'ColOrder':1600," +
            "'ColType':'VARCHAR'," +
            "'ColWidth':50," +
            "'ColPrecision':0," +
            "'ColNull':1," +
            "'ColDefault':''," +
            "'DisplayLabel':'加班报餐'," +
            "'DisplayWidth':0," +
            "'DisplayFormat':''," +
            "'EditFormat':''," +
            "'RelationRule':''," +
            "'ColVisible':0," +
            "'ColProperty':3," +
            "'ColRight':1," +
            "'ColGroup':''," +
            "'enus':''," +
            "'zhtw':''," +
            "'OtherLanguage':''," +
            "'IsVisible':true," +
            "'Tag':null" +
            "}]," +
            "'ColVarifyExprList':[" +
            "]," +
            "'ColFormulaExprList':[" +
            "]," +
            "'TableStyleRuleList':[" +
            "]," +
            "'Tag':null," +
            "'TableGroup':null," +
            "'RightType':0" +
            "}";

    public static final String TIAO_XIU_TABLES = "{" +
            "    'TableName': 'DATA'," +
            "    'TableTypeID': 27," +
            "    'TableOrder': 4," +
            "    'TableLabel': '请假'," +
            "    'ACessable': '111'," +
            "    'ACessModule': '010001000000000000000000000000'," +
            "    'TableVisible': 3," +
            "    'ModuleID': '05'," +
            "    'TableDes': null," +
            "    'Grades': 0," +
            "    'Presere': null," +
            "    'enus': null," +
            "    'zhtw': null," +
            "    'OtherLanguage': null," +
            "    'FieldList': [{" +
            "        'ColumnID': 1471," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'K_ID'," +
            "        'ColOrder': 0," +
            "        'ColType': 'INT IDENTITY'," +
            "        'ColWidth': 4," +
            "        'ColPrecision': 0," +
            "        'ColNull': 0," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '请假ID'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': ''," +
            "        'RelationRule': ''," +
            "        'ColVisible': 0," +
            "        'ColProperty': 1," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '請假ID'," +
            "        'OtherLanguage': '休憩取るID'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 15607," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_REASON'," +
            "        'ColOrder': 11," +
            "        'ColType': 'VARCHAR'," +
            "        'ColWidth': 400," +
            "        'ColPrecision': 0," +
            "        'ColNull': 1," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '请假原因'," +
            "        'DisplayWidth': 0," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': 'MEMO(200,60)'," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': ''," +
            "        'OtherLanguage': ''," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1472," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'A0188'," +
            "        'ColOrder': 100," +
            "        'ColType': 'INT'," +
            "        'ColWidth': 4," +
            "        'ColPrecision': 0," +
            "        'ColNull': 0," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '人员姓名'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': 'LOOK(A01|A0188|A0101|)'," +
            "        'RelationRule': ''," +
            "        'ColVisible': 2," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '人員姓名'," +
            "        'OtherLanguage': '従業員氏名'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1476," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_TYPE'," +
            "        'ColOrder': 200," +
            "        'ColType': 'CHAR'," +
            "        'ColWidth': 2," +
            "        'ColPrecision': 0," +
            "        'ColNull': 1," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '请假类别'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': 'CODE(BM_LEAVE|1)'," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '請假類別'," +
            "        'OtherLanguage': '休暇申込類別'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1473," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_BEGIN'," +
            "        'ColOrder': 300," +
            "        'ColType': 'DATETIME'," +
            "        'ColWidth': 8," +
            "        'ColPrecision': 0," +
            "        'ColNull': 1," +
            "        'ColDefault': '09:00'," +
            "        'DisplayLabel': '请假开始日期'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': 'yyyy-MM-dd HH:mm'," +
            "        'EditFormat': ''," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '請假開始日期'," +
            "        'OtherLanguage': '休暇申込開始期日'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1474," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_END'," +
            "        'ColOrder': 400," +
            "        'ColType': 'DATETIME'," +
            "        'ColWidth': 8," +
            "        'ColPrecision': 0," +
            "        'ColNull': 1," +
            "        'ColDefault': '18:00'," +
            "        'DisplayLabel': '请假结束日期'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': 'yyyy-MM-dd HH:mm'," +
            "        'EditFormat': ''," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '請假結束日期'," +
            "        'OtherLanguage': '休暇申込終了期日'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1475," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_TIME'," +
            "        'ColOrder': 500," +
            "        'ColType': 'DECIMAL'," +
            "        'ColWidth': 9," +
            "        'ColPrecision': 2," +
            "        'ColNull': 0," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '请假时间长度'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': ''," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '請假時間長度'," +
            "        'OtherLanguage': '休暇申込時間長さ'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 8483," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVE_DAYS'," +
            "        'ColOrder': 600," +
            "        'ColType': 'NUMERIC'," +
            "        'ColWidth': 18," +
            "        'ColPrecision': 2," +
            "        'ColNull': 0," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '请假天数'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': ''," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': ''," +
            "        'OtherLanguage': '休暇申込日数'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 11480," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'LEAVEFILE'," +
            "        'ColOrder': 650," +
            "        'ColType': 'VARCHAR'," +
            "        'ColWidth': 1000," +
            "        'ColPrecision': 0," +
            "        'ColNull': 1," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '附件'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': 'DOCUMENT(ALL FILES(*.*)|*.*&5&5)'," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': ''," +
            "        'OtherLanguage': '添付文書'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }, {" +
            "        'ColumnID': 1477," +
            "        'TableName': 'K_LEAVE'," +
            "        'TableTypeId': 0," +
            "        'ColName': 'SIGNED'," +
            "        'ColOrder': 1000," +
            "        'ColType': 'CHAR'," +
            "        'ColWidth': 1," +
            "        'ColPrecision': 0," +
            "        'ColNull': 0," +
            "        'ColDefault': ''," +
            "        'DisplayLabel': '审批状态'," +
            "        'DisplayWidth': 80," +
            "        'DisplayFormat': ''," +
            "        'EditFormat': 'CODE(BM_SP|1)'," +
            "        'RelationRule': ''," +
            "        'ColVisible': 1," +
            "        'ColProperty': 2," +
            "        'ColRight': 1," +
            "        'ColGroup': '1、请假基本信息'," +
            "        'enus': ''," +
            "        'zhtw': '審核標記'," +
            "        'OtherLanguage': '審査マーク'," +
            "        'IsVisible': true," +
            "        'Tag': null" +
            "    }]," +
            "    'ColVarifyExprList': []," +
            "    'ColFormulaExprList': []," +
            "    'TableStyleRuleList': []," +
            "    'Tag': null," +
            "    'TableGroup': null," +
            "    'RightType': 0" +
            "}";

    public static final String TIAO_XIU_J_ORIGINAL_DATA_XML = "{" +
            "    'DataSet': {" +
            "        'DATA': {" +
            "            'K_LEAVE_K_ID': ''," +
            "            'K_LEAVE_LEAVE_REASON': ''," +
            "            'K_LEAVE_A0188': ''," +
            "            'K_LEAVE_LEAVE_TYPE': ''," +
            "            'K_LEAVE_LEAVE_BEGIN': ''," +
            "            'K_LEAVE_LEAVE_END': ''," +
            "            'K_LEAVE_LEAVE_TIME': ''," +
            "            'K_LEAVE_LEAVE_DAYS': ''," +
            "            'K_LEAVE_LEAVEFILE': ''," +
            "            'K_LEAVE_SIGNED': ''," +
            "            'DataRightType': '0'," +
            "            'ValidateState': '1'," +
            "            'FormulaState': '1'," +
            "            'SequenceID': '0'," +
            "            '@RowState': 'Added'" +
            "        }" +
            "    }" +
            "}";


}
