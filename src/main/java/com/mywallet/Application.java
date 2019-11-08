package com.mywallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.PrintStream;

/**
 * Main
 *
 * @author linapex
 */
@EnableScheduling
@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBanner(new Banner() {
            @Override
            public void printBanner(Environment arg0, Class<?> arg1, PrintStream arg2) {
                StringBuilder console = new StringBuilder();
                console.append("//                            _ooOoo_  \t\n");
                console.append("//                           o8888888o  \t\n");
                console.append("//                           88\" . \"88  \t\n");
                console.append("//                           (| -_- |)  \t\n");
                console.append("//                            O\\ = /O  \t\n");
                console.append("//                        ____/`---'\\____  \t\n");
                console.append("//                      .   ' \\| |// `.  \t\n");
                console.append("//                       / \\||| : |||// \\  \t\n");
                console.append("//                     / _||||| -:- |||||- \\  \t\n");
                console.append("//                       | | \\\\ - /// | |  \t\n");
                console.append("//                     | \\_| ''\\---/'' | |  \t\n");
                console.append("//                      \\ .-\\__ `-` ___/-. /  \t\n");
                console.append("//                   ___`. .' /--.--\\ `. . __  \t\n");
                console.append("//                .\\'\' '< `.___\\_<|>_/___.' >'\"\".  \t\n");
                console.append("//               | | : `- \\`.;`\\ _ /`;.`/ - ` : | |  \t\n");
                console.append("//                 \\ \\ `-. \\_ __\\ /__ _/ .-` / /  \t\n");
                console.append("//         ======`-.____`-.___\\_____/___.-`____.-'======  \t\n");
                console.append("//                            `=---='  \t\n");
                console.append("//  \t\n");
                console.append("//         .............................................  \t\n");
                console.append("//                  佛祖镇楼                  BUG辟易  \t\n");
                console.append("//          佛曰:  \t\n");
                console.append("//                  写字楼里写字间，写字间里程序员；  \t\n");
                console.append("//                  程序人员写程序，又拿程序换酒钱。  \t\n");
                console.append("//                  酒醒只在网上坐，酒醉还来网下眠；  \t\n");
                console.append("//                  酒醉酒醒日复日，网上网下年复年。  \t\n");
                console.append("//                  但愿老死电脑间，不愿鞠躬老板前；  \t\n");
                console.append("//                  奔驰宝马贵者趣，公交自行程序员。  \t\n");
                console.append("//                  别人笑我忒疯癫，我笑自己命太贱；  \t\n");
                console.append("//                  不见满街漂亮妹，哪个归得程序员？  \t\n");
                arg2.println(console);
            }
        });
        application.run(args);
        logger.info("PortalApplication is success!");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    final static Logger logger = LoggerFactory.getLogger(Application.class);
}
