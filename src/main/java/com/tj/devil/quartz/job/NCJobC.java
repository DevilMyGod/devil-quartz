package com.tj.devil.quartz.job;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tj.devil.quartz.util.WriteEXCEL;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Devil
 * @date 2017/11/3 21:59
 */
@Component
public class NCJobC {

    @Scheduled(cron = "0 20 22 * * ?")
    public void execute() {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            String path = "E:/exports/emp_info_active.xls";
            String sql = "select LTRIM(c.clerkcode, 0) as clerkcode, a.name, a.mobile, b.user_code, '天九平台投资集团有限公司'||'/'|| case when d5.name is not null and d4.name is not null then d5.name||'/'||d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d4.name is not null and d2.name is not null then d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is not null and d2.name is not null then d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is null and d2.name is not null then d2.name||'/'||d1.name||'/'||d.name when d2.name is null and d1.name is not null then d1.name||'/'||d.name when d1.name is null and d.name is not null then d.name else d.name end as org, substr(a.id,length(a.id)-5,6) as identy, 1 as state from bd_psndoc a left join sm_user b on a.pk_psndoc=b.pk_psndoc left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join org_dept d on c.pk_dept=d.pk_dept left join org_dept d1 on d.pk_fatherorg=d1.pk_dept left join org_dept d2 on d1.pk_fatherorg=d2.pk_dept left join org_dept d3 on d2.pk_fatherorg=d3.pk_dept left join org_dept d4 on d3.pk_fatherorg=d4.pk_dept left join org_dept d5 on d4.pk_fatherorg=d5.pk_dept left join hi_psnorg e on e.pk_psndoc=a.pk_psndoc and e.pk_psnorg = c.pk_psnorg where (c.endflag='N' or e.endflag='N') and c.lastflag ='Y' and e.lastflag = 'Y' and a.pk_org='0001A810000000000H9W' and a.enablestate = 2 and to_date(a.creationtime, 'yyyy-mm-dd hh24:mi:ss') < sysdate and to_date(a.creationtime,'yyyy-mm-dd hh24:mi:ss') > to_date(to_char(TRUNC(SYSDATE-1),'yyyy-mm-dd') || '00:00:00','yyyy-mm-dd hh24:mi:ss') UNION select LTRIM(c.clerkcode, 0) as clerkcode, a.name, a.mobile, b.user_code, '天九平台投资集团有限公司'||'/'|| case when d5.name is not null and d4.name is not null then d5.name||'/'||d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d4.name is not null and d2.name is not null then d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is not null and d2.name is not null then d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is null and d2.name is not null then d2.name||'/'||d1.name||'/'||d.name when d2.name is null and d1.name is not null then d1.name||'/'||d.name when d1.name is null and d.name is not null then d.name else d.name end as org, substr(a.id,length(a.id)-5,6) as identy, 2 as state from bd_psndoc a left join sm_user b on a.pk_psndoc=b.pk_psndoc left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join org_dept d on c.pk_dept=d.pk_dept left join org_dept d1 on d.pk_fatherorg=d1.pk_dept left join org_dept d2 on d1.pk_fatherorg=d2.pk_dept left join org_dept d3 on d2.pk_fatherorg=d3.pk_dept left join org_dept d4 on d3.pk_fatherorg=d4.pk_dept left join org_dept d5 on d4.pk_fatherorg=d5.pk_dept left join hi_psnorg e on e.pk_psndoc=a.pk_psndoc and e.pk_psnorg = c.pk_psnorg and e.pk_hrorg = c.pk_hrorg where (c.endflag='N' or e.endflag='N') and c.lastflag ='Y' and e.lastflag = 'Y' and a.pk_org='0001A810000000000H9W' and a.enablestate = 2 and to_date(a.ts, 'yyyy-mm-dd hh24:mi:ss') < sysdate and to_date(a.ts, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(TRUNC(SYSDATE-1),'yyyy-mm-dd') || '00:00:00','yyyy-mm-dd hh24:mi:ss') and a.pk_psndoc not in (select a.pk_psndoc from bd_psndoc a left join sm_user b on a.pk_psndoc=b.pk_psndoc left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join org_dept d on c.pk_dept=d.pk_dept left join org_dept d1 on d.pk_fatherorg=d1.pk_dept left join org_dept d2 on d1.pk_fatherorg=d2.pk_dept left join org_dept d3 on d2.pk_fatherorg=d3.pk_dept left join org_dept d4 on d3.pk_fatherorg=d4.pk_dept left join org_dept d5 on d4.pk_fatherorg=d5.pk_dept left join hi_psnorg e on e.pk_psndoc=a.pk_psndoc and e.pk_psnorg = c.pk_psnorg where (c.endflag='N' or e.endflag='N') and c.lastflag ='Y' and e.lastflag = 'Y' and a.pk_org='0001A810000000000H9W' and a.enablestate = 2 and to_date(a.creationtime, 'yyyy-mm-dd hh24:mi:ss') < sysdate and to_date(a.creationtime,'yyyy-mm-dd hh24:mi:ss') > to_date(to_char(TRUNC(SYSDATE-1),'yyyy-mm-dd') || '00:00:00','yyyy-mm-dd hh24:mi:ss') )";
            List<Record> records = Db.find(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            WriteEXCEL writeEXCEL = new WriteEXCEL("在职人员导出", "在职", sdf.format(new Date()), workbook);
            LinkedHashMap<String, String> titleMap = new LinkedHashMap<>();
            titleMap.put("CLERKCODE", "员工编号,6");
            titleMap.put("NAME", "员工姓名,6");
            titleMap.put("MOBILE", "手机号,6");
            titleMap.put("USER_CODE", "登陆帐号,6");
            titleMap.put("ORG", "组织,6");
            titleMap.put("IDENTY", "身份后六位,6");
            titleMap.put("STATE", "状态,6");
            System.out.println("开始在职人员文件导出");
            writeEXCEL.write(records, titleMap, "在职", path);
            System.out.println("结束在职人员文件导出");
            String sql1 = "select LTRIM(c.clerkcode, 0) as clerkcode, a.name, a.mobile, b.user_code, '天九平台投资集团有限公司'||'/'|| case when d5.name is not null and d4.name is not null then d5.name||'/'||d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d4.name is not null and d2.name is not null then d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is not null and d2.name is not null then d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is null and d2.name is not null then d2.name||'/'||d1.name||'/'||d.name when d2.name is null and d1.name is not null then d1.name||'/'||d.name when d1.name is null and d.name is not null then d.name else d.name end as org, substr(a.id,length(a.id)-5,6) as identy, 3 as state from bd_psndoc a left join sm_user b on a.pk_psndoc=b.pk_psndoc left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join org_dept d on c.pk_dept=d.pk_dept left join org_dept d1 on d.pk_fatherorg=d1.pk_dept left join org_dept d2 on d1.pk_fatherorg=d2.pk_dept left join org_dept d3 on d2.pk_fatherorg=d3.pk_dept left join org_dept d4 on d3.pk_fatherorg=d4.pk_dept left join org_dept d5 on d4.pk_fatherorg=d5.pk_dept left join hi_psnorg e on e.pk_psndoc=a.pk_psndoc and e.pk_psnorg=c.pk_psnorg and e.pk_hrorg = c.pk_hrorg where c.endflag='Y' and e.endflag='Y' and c.lastflag ='Y' and a.pk_org='0001A810000000000H9W' and a.enablestate = 2 and to_date(a.modifiedtime, 'yyyy-mm-dd hh24:mi:ss') < sysdate and to_date(a.modifiedtime, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(TRUNC(SYSDATE-1),'yyyy-mm-dd') || '00:00:00','yyyy-mm-dd hh24:mi:ss') and a.pk_psndoc not in (select a.pk_psndoc from bd_psndoc a left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join hi_psnorg e on e.pk_psndoc=a.pk_psndoc where (c.endflag='N' or e.endflag='N') and c.lastflag='Y' and a.enablestate=2)";
            records = Db.find(sql1);
            titleMap.clear();
            titleMap.put("CLERKCODE", "员工编号,6");
            titleMap.put("NAME", "员工姓名,6");
            titleMap.put("MOBILE", "手机号,6");
            titleMap.put("USER_CODE", "登陆帐号,6");
            titleMap.put("ORG", "组织,6");
            titleMap.put("IDENTY", "身份后六位,6");
            titleMap.put("STATE", "状态,6");
            System.out.println("开始离职人员文件导出");
            writeEXCEL.write(records, titleMap, "离职", path);
            System.out.println("结束离职人员文件导出");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
