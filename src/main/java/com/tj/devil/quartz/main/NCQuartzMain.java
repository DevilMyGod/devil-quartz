package com.tj.devil.quartz.main;

import com.tj.devil.quartz.util.DBHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * NC定时器
 *
 * @author Devil
 * @date 2017/10/24 16:55
 */
public class NCQuartzMain {

    public static void main(String[] args) {
        DBHelper.init();
        String paths[] = { "applicationContext.xml" };
        @SuppressWarnings({ "resource", "unused" })
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
    }

}
