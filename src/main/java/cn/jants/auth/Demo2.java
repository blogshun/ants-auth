package cn.jants.auth;

import cn.jants.common.utils.StrCaseUtil;
import cn.jants.core.utils.ScanUtil;
import cn.jants.plugin.orm.Table;
import cn.jants.plugin.orm.TableBean;
import cn.jants.plugin.orm.TableMapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author MrShun
 * @version 1.0
 * @Date 2018/1/5
 */
public class Demo2 {

    public static void main(String[] args) {
        List<Class<?>> scanClass = ScanUtil.findScanClass(new String[]{"com.ants.auth"}, Table.class);
        for (Class<?> scanCls : scanClass) {
            TableBean tableBean = TableMapper.findTableBean(scanCls);
            System.out.println(tableBean.getTable());
            testJavaPoet(tableBean, scanCls);
        }

    }

    /**
     * 库: https://github.com/square/javapoet/
     * 使用下面语句引用javapoet (仓库为jcenter):
     * compile 'com.squareup:javawriter:2.5.1'
     * <p>
     * 使用javapoet生成java源文件的步骤 (1,2,3步骤可以交换):
     * 1. 构建成员变量
     * 2. 构建构造方法
     * 3. 构建方法(static/concrete)
     * 4. 构建类型(enum/annotation/interface/class)
     * 5. 构建java源文件
     * 6. 输出java源文件到文件系统
     */
    private static void testJavaPoet(TableBean tableBean, Class cls) {
        String packageName = "com.ants.auth.generate";
        String className = "Q".concat(cls.getSimpleName());
        String tableName = tableBean.getTable();
        //生成类型
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        FieldSpec tableFieldSpec = FieldSpec.builder(String.class, "TABLE", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", tableName)
                .build();
        builder.addField(tableFieldSpec);
        for (String filed : tableBean.getFields()) {
            FieldSpec fieldSpec = FieldSpec.builder(String.class, filed.toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", filed)
                    .build();

            builder.addField(fieldSpec);

            StringBuffer sb = new StringBuffer();
            sb.append(filed).append(" as ").append(StrCaseUtil.toCapital(filed, false));
            FieldSpec fieldAsSpec = FieldSpec.builder(String.class, "AS_".concat(filed.toUpperCase()), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", sb.toString())
                    .build();

            builder.addField(fieldAsSpec);

        }
        TypeSpec hellworld = builder.build();
        //构建Java源文件
        JavaFile javaFile = JavaFile.builder(packageName, hellworld).build();

        //5. 输出java源文件到文件系统
        try {
            System.out.println(javaFile);
            //输出到和用例程序相同的源码目录下
            String targetDirectory = "../ants-auth/src/main/java/";
            File dir = new File(targetDirectory);
            System.out.println(dir.getPath());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //JavaFile.write(), 参数为源码生成目录(源码的classpath目录)
            javaFile.writeTo(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
