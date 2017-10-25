package com.tj.devil.quartz.job;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tj.devil.quartz.util.WriteEXCEL;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * NC数据拉取定时任务
 *
 * @author Devil
 * @date 2017/10/24 16:43
 */
@Component
public class NCJobA {

    @Scheduled(cron = "0 35 03 * * ?")
    public void execute() {
        try {
            String path = "D:/exports/emp_info_active.xls";
            String sql = "select LTRIM(c.clerkcode, 0) as clerkcode, a.name, a.mobile, b.user_code, case when d5.name is not null and d4.name is not null then d5.name||'/'||d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d4.name is not null and d2.name is not null then d4.name||'/'||d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is not null and d2.name is not null then d3.name||'/'||d2.name||'/'||d1.name||'/'||d.name when d3.name is null and d2.name is not null then d2.name||'/'||d1.name||'/'||d.name when d2.name is null and d1.name is not null then d1.name||'/'||d.name when d1.name is null and d.name is not null then d.name else d.name end as org, substr(a.id,length(a.id)-6,6) as identy from bd_psndoc a left join sm_user b on a.pk_psndoc=b.pk_psndoc left join hi_psnjob c on c.pk_psndoc=a.pk_psndoc left join org_dept d on c.pk_dept=d.pk_dept left join org_dept d1 on d.pk_fatherorg=d1.pk_dept left join org_dept d2 on d1.pk_fatherorg=d2.pk_dept left join org_dept d3 on d2.pk_fatherorg=d3.pk_dept left join org_dept d4 on d3.pk_fatherorg = d4.pk_dept left join org_dept d5 on d4.pk_fatherorg = d5.pk_dept where c.trnsevent = 1 and a.enablestate = 2";
            List<Record> records = Db.find(sql);
            System.out.println(records.size());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            WriteEXCEL writeEXCEL = new WriteEXCEL("在职人员导出", "在职", sdf.format(new Date()));
            LinkedHashMap<String, String> titleMap = new LinkedHashMap<>();
            titleMap.put("CLERKCODE", "员工编号,6");
            titleMap.put("NAME", "员工姓名,6");
            titleMap.put("MOBILE", "手机号,6");
            titleMap.put("USER_CODE", "登陆帐号,6");
            titleMap.put("ORG", "组织,6");
            titleMap.put("IDENTY", "身份后六位,6");
            System.out.println("开始文件导出");
            writeEXCEL.write(records, titleMap, "在职", path);
            System.out.println("结束文件导出");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
