package fun.haolo.bigLandlord.db.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * @Author haolo
 * @Date 2022-10-13 12:56
 * @Description
 */
public class Generator {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(
                        "jdbc:mysql://127.0.0.1:3306/big_landlord?useSSL=false&useUnicode=true&characterEncoding=UTF-8&severTimezone=GMT%2B8",
                        "root",
                        "123456")
                .globalConfig(builder -> {
                    builder.author("haolo") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/bigLandlord-db/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("fun.haolo.bigLandlord.db"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("bl_deposit") // 设置需要生成的表名
                            .addTablePrefix("bl_") // 设置过滤表前缀
                            .entityBuilder()
//                            .enableLombok()//开启 lombok 模型
                            .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time",FieldFill.INSERT_UPDATE))
                            .logicDeleteColumnName("deleted");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
