package com.Oovever.esayTool.io;

import com.Oovever.esayTool.util.StringUtil;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author OovEver
 * 2018/6/8 17:01
 */
public class FileUtilTest {

    @Test
    public void touch() {
        String path = "D:\\tmp\\generator-result\\src\\main\\resources";
        File file = new File(path);
        FileUtil.del(file);
    }
}