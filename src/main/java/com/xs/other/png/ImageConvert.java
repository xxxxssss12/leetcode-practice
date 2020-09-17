package com.xs.other.png;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.exec.*;

import java.io.*;

/**
 * @author xiongshun
 * create-time: 2020-09-14 19:47
 */
public class ImageConvert {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String CMD_PATH = "D:/Applications/ImageMagick-7.0.10-Q16-HDRI/magick.exe";
    private static final String CONERT_FORMAT = "%s convert %s %s";
    private static final String INFO_FORMAT = "%s identify %s";

    public static ExecResult execConvert(String originFile, String targetFile) throws IOException {
        String executeCmd = String.format(CONERT_FORMAT, CMD_PATH, originFile, targetFile);
        return exeCommand(executeCmd);
    }

    public static ExecResult execInfo(String originFile) throws IOException {
        String executeCmd = String.format(INFO_FORMAT, CMD_PATH, originFile);
        return exeCommand(executeCmd);
    }
    public static void main(String[] args) throws IOException {
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.tif"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.dng"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.heic"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.eps"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.CR2"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/real.NEF"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execInfo("D:/Pictures/aibrain/touming.png"), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(execConvert("D:/Pictures/aibrain/real.tif", "D:/Pictures/aibrain/result-tif.png"), SerializerFeature.PrettyFormat));
    }

    /**
     * 执行指定命令
     *
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static ExecResult exeCommand(String command) throws IOException {
        long start = System.currentTimeMillis();
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            int exitCode = exeCommand(command, out);
            if (exitCode == 0) {
                System.out.println("命令运行成功！" + command + ";耗时=" + (System.currentTimeMillis() - start));
            } else {
                System.out.println("命令运行失败！" + command + ";耗时=" + (System.currentTimeMillis() - start));
            }
            String msg = out.toString(DEFAULT_CHARSET);
            ExecResult result = new ExecResult();
            result.setResultMsg(msg);
            result.setCode(exitCode);
            return result;
        } catch (ExecuteException e) {
            ExecResult result = new ExecResult();
//            result.setResultMsg(msg);
            result.setCode(e.getExitValue());
            System.out.println("命令运行失败！" + command + ";exitValue=" + e.getExitValue());
            return result;
        }
    }

    /**
     * 执行指定命令，输出结果到指定输出流中
     *
     * @param command 命令
     * @param out 执行结果输出流
     * @return 执行结果状态码：执行成功返回0
     * @throws ExecuteException 失败时抛出异常，由调用者捕获处理
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static int exeCommand(String command, OutputStream out) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler pumpStreamHandler = null;
        if (null == out) {
            pumpStreamHandler = new PumpStreamHandler();
        } else {
            pumpStreamHandler = new PumpStreamHandler(out);
        }

        // 设置超时时间为60秒
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        executor.setWatchdog(watchdog);

        return executor.execute(commandLine);
    }
}
