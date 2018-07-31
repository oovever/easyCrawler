package com.Oovever.esayTool.io.file;

import com.Oovever.esayTool.io.FileUtil;
import com.Oovever.esayTool.io.IORuntimeException;
import com.Oovever.esayTool.io.IoUtil;
import com.Oovever.esayTool.io.LineHandler;
import com.Oovever.esayTool.util.CharsetUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author OovEver
 * 2018/6/9 23:11
 */
public class FileReader extends FileWrapper {

    /**
     * 构造
     * @param file 文件
     * @param charset 编码
     */
    public FileReader(File file, Charset charset) {
        super(file, charset);
        checkFile();
    }
    /**
     * 构造
     * @param filePath 文件路径，相对路径会被转换为相对于ClassPath的路径
     * @param charset 编码，使用 {@link CharsetUtil}
     */
    public FileReader(String filePath, Charset charset) {
        this(FileUtil.file(filePath), charset);
    }
    /**
     * 构造<br>
     * 编码使用 {@link FileWrapper#DEFAULT_CHARSET}
     * @param filePath 文件路径，相对路径会被转换为相对于ClassPath的路径
     */
    public FileReader(String filePath) {
        this(filePath, DEFAULT_CHARSET);
    }

    /**
     * 构造
     * @param filePath 文件路径，相对路径会被转换为相对于ClassPath的路径
     * @param charset 编码，使用 {@link CharsetUtil#charset(String)}
     */
    public FileReader(String filePath, String charset) {
        this(FileUtil.file(filePath), CharsetUtil.charset(charset));
    }
    /**
     * 构造
     * @param file 文件
     */
    public FileReader(File file) {
        this(file, DEFAULT_CHARSET);
    }

    /**
     * 构造
     * @param file 文件
     * @param charset 编码，使用 {@link CharsetUtil#charset(String)}
     */
    public FileReader(File file, String charset) {
        this(file, CharsetUtil.charset(charset));
    }
    /**
     * 创建 FileReader
     * @param file 文件
     * @param charset 编码
     * @return {@link FileReader}
     */
    public static FileReader create(File file, Charset charset){
        return new FileReader(file, charset);
    }
    /**
     * 创建 FileReader
     * @param file 文件
     * @return {@link FileReader}
     */
    public static FileReader create(File file){
        return new FileReader(file);
    }

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过 {@link Integer#MAX_VALUE}
     *
     * @return 字节码
     * @throws IORuntimeException IO异常
     */
    public byte[] readBytes() throws IORuntimeException {
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IORuntimeException("File is larger then max array size");
        }

        byte[]          bytes = new byte[(int) len];
        FileInputStream in    = null;
        int             readLength;
        try {
            in = new FileInputStream(file);
            readLength = in.read(bytes);
            if(readLength < len){
                throw new IOException("读取字符长度超限");
            }
        } catch (Exception e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(in);
        }

        return bytes;
    }
    /**
     * 读取文件内容
     *
     * @return 内容
     * @throws IORuntimeException IO异常
     */
    public String readString() throws IORuntimeException{
        return new String(readBytes(), this.charset);
    }
    /**
     * 从文件中读取每一行数据
     *
     * @param <T> 集合类型
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public <T extends Collection<String>> T readLines(T collection) throws IORuntimeException {
        BufferedReader reader = null;
        try {
            reader = FileUtil.getReader(file, charset);
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                collection.add(line);
            }
            return collection;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(reader);
        }
    }

    /**
     * 按照行处理文件内容
     *
     * @param lineHandler 行处理器
     * @throws IORuntimeException IO异常
     */
    public void readLines(LineHandler lineHandler) throws IORuntimeException{
        BufferedReader reader = null;
        try {
            reader = FileUtil.getReader(file, charset);
            IoUtil.readLines(reader, lineHandler);
        } finally {
            IoUtil.close(reader);
        }
    }
    /**
     * 从文件中读取每一行数据
     *
     * @return 文件中的每行内容的集合
     * @throws IORuntimeException IO异常
     */
    public List<String> readLines() throws IORuntimeException {
        return readLines(new ArrayList<String>());
    }
    /**
     * 获得一个文件读取器
     *
     * @return BufferedReader对象
     * @throws IORuntimeException IO异常
     */
    public BufferedReader getReader() throws IORuntimeException {
        return IoUtil.getReader(getInputStream(), this.charset);
    }
    /**
     * 获得输入流
     *
     * @return 输入流
     * @throws IORuntimeException IO异常
     */
    public BufferedInputStream getInputStream() throws IORuntimeException {
        try {
            return new BufferedInputStream(new FileInputStream(this.file));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 将文件写入流中
     *
     * @param out 流
     * @return File
     * @throws IORuntimeException IO异常
     */
    public File writeToStream(OutputStream out) throws IORuntimeException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            IoUtil.copy(in, out);
        }catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(in);
        }
        return this.file;
    }
    /**
     * 检查文件
     *
     * @throws IORuntimeException IO异常
     */
    private void checkFile() throws IORuntimeException {
        if (false == file.exists()) {
            throw new IORuntimeException("File not exist: " + file);
        }
        if (false == file.isFile()) {
            throw new IORuntimeException("Not a file:" + file);
        }
    }


}
