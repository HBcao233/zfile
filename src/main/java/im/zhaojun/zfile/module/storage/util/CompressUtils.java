package im.zhaojun.zfile.module.storage.util;

import im.zhaojun.zfile.module.storage.model.enums.CompressTypeEnum;
import im.zhaojun.zfile.module.storage.model.enums.FileTypeEnum;
import im.zhaojun.zfile.module.storage.model.result.CompressedItemResult;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;

interface Compressor {
  /**
   * 筛选存在的路径并创建 UTF-8 编码的 ZIP
   *
   * @param path 路径
   * @param nameList 文件名列表
   * @param fileName 输出的压缩文件名
   * @param splitSizeMB 每个分卷的大小（MB）
   */
  default boolean compress(String path, List<String> nameList, String fileName) {
    return compress(path, nameList, fileName, null);
  }

  boolean compress(String path, List<String> nameList, String fileName, Long splitSizeMB);

  /**
   * 筛选存在的路径并创建 UTF-8 编码的 ZIP
   *
   * @param path 路径
   * @param name 压缩包文件名
   * @param output 输出文件夹
   * @param password 解压密码
   * @return 是否解压成功
   */
  default boolean decompress(String path, String fileName, String destDir) {
    return decompress(path, fileName, destDir, "");
  }

  default boolean decompress(String path, String fileName, String destDir, String password) {
    return decompress(path, fileName, destDir, password, StandardCharsets.UTF_8);
  }

  boolean decompress(
      String path, String fileName, String destDir, String password, Charset charset);

  /** 列出压缩包文件 */
  List<CompressedItemResult> listContents(String path, String fileName);
}

@Slf4j
class ZipCompressor implements Compressor {
  @Override
  public boolean compress(String path, List<String> nameList, String fileName, Long splitSizeMB) {
    // 筛选存在的所有文件
    List<File> files =
        nameList.stream()
            .map(name -> new File(path, name))
            .filter(file -> file.exists())
            .flatMap(
                file -> {
                  if (file.isFile()) {
                    return Stream.of(file);
                  }
                  try {
                    return Files.walk(Paths.get(file.getPath()))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile);
                  } catch (IOException e) {
                    return Stream.empty();
                  }
                })
            .collect(Collectors.toList());

    if (files.isEmpty()) {
      return false;
    }

    // 创建 UTF-8 编码的 ZIP
    File output = new File(path, fileName);
    try {
      ZipFile zipFile = new ZipFile(output);
      zipFile.setCharset(StandardCharsets.UTF_8);

      // 设置压缩参数
      ZipParameters parameters = new ZipParameters();
      parameters.setDefaultFolderPath(path);
      parameters.setCompressionLevel(CompressionLevel.NORMAL);

      boolean isSplit = (splitSizeMB != null && splitSizeMB > 0L);
      long splitSizeBytes = isSplit ? splitSizeMB * 1024 * 1024 : 0L;
      zipFile.createSplitZipFile(files, parameters, isSplit, splitSizeBytes);
    } catch (ZipException e) {
      log.error("创建zip失败: {}", e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public boolean decompress(
      String path, String fileName, String destDir, String password, Charset charset) {
    File file = new File(path, fileName);
    try {
      ZipFile zip = new ZipFile(file);
      if (charset == null) {
        charset = StandardCharsets.UTF_8;
      }
      zip.setCharset(charset);
      if (password != null && !password.isEmpty()) {
        zip.setPassword(password.toCharArray());
      }

      zip.extractAll(destDir);
    } catch (ZipException e) {
      log.error("解压 {} 失败: {}", fileName, e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public List<CompressedItemResult> listContents(String path, String fileName) {
    List<CompressedItemResult> items = new ArrayList<>();
    File file = new File(path, fileName);
    try {
      ZipFile zipFile = new ZipFile(file);
      List<FileHeader> headers = zipFile.getFileHeaders();

      for (FileHeader header : headers) {
        String fname = header.getFileName();
        // 只获取第一层的文件和文件夹
        if (!isFirstLevel(fname)) continue;

        String displayName = getFirstLevelName(fname);
        // 检查是否已经添加过（文件夹可能重复）
        if (containsItem(items, displayName)) continue;

        CompressedItemResult item =
            new CompressedItemResult(
                displayName,
                header.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE,
                header.isEncrypted(),
                header.getUncompressedSize());
        items.add(item);
      }
    } catch (ZipException e) {
    }
    return items;
  }

  /** 判断是否是第一层 */
  private static boolean isFirstLevel(String fileName) {
    // 去掉末尾的斜杠（如果是目录）
    String normalized =
        fileName.endsWith("/") ? fileName.substring(0, fileName.length() - 1) : fileName;

    // 第一层：不包含斜杠，或者只有一个末尾斜杠
    return !normalized.contains("/");
  }

  /** 获取第一层名称 */
  private static String getFirstLevelName(String fileName) {
    if (fileName.contains("/")) {
      // 取第一个斜杠前的部分
      return fileName.substring(0, fileName.indexOf("/"));
    }
    return fileName;
  }

  /** 检查列表中是否已包含该项 */
  private static boolean containsItem(List<CompressedItemResult> items, String name) {
    return items.stream().anyMatch(item -> item.getName().equals(name));
  }
}

class SevenZipCompressor implements Compressor {
  @Override
  public boolean compress(String path, List<String> nameList, String fileName, Long splitSizeMB) {
    // TODO
    return false;
  }

  @Override
  public boolean decompress(
      String path, String fileName, String destDir, String password, Charset charset) {
    // TODO
    return false;
  }

  @Override
  public List<CompressedItemResult> listContents(String path, String fileName) {
    // TODO
    throw new UnsupportedOperationException("未实现");
  }
}

class RarCompressor implements Compressor {
  @Override
  public boolean compress(String path, List<String> nameList, String fileName, Long splitSizeMB) {
    // TODO
    return false;
  }

  @Override
  public boolean decompress(
      String path, String fileName, String destDir, String password, Charset charset) {
    // TODO
    return false;
  }

  @Override
  public List<CompressedItemResult> listContents(String path, String fileName) {
    // TODO
    throw new UnsupportedOperationException("未实现");
  }
}

class GzipCompressor implements Compressor {
  @Override
  public boolean compress(String path, List<String> nameList, String fileName, Long splitSizeMB) {
    // TODO
    return false;
  }

  @Override
  public boolean decompress(
      String path, String fileName, String destDir, String password, Charset charset) {
    // TODO
    return false;
  }

  @Override
  public List<CompressedItemResult> listContents(String path, String fileName) {
    // TODO
    throw new UnsupportedOperationException("未实现");
  }
}

/**
 * 压缩相关工具类
 *
 * @author HBcao233
 */
public class CompressUtils {
  private static final Map<String, Compressor> compressors = new HashMap<>();

  static {
    compressors.put(CompressTypeEnum.ZIP.getKey(), new ZipCompressor());
    compressors.put(CompressTypeEnum.SEVEN_Z.getKey(), new SevenZipCompressor());
    compressors.put(CompressTypeEnum.RAR.getKey(), new RarCompressor());
    compressors.put(CompressTypeEnum.GZ.getKey(), new GzipCompressor());
  }

  public static boolean compress(
      CompressTypeEnum type, String path, List<String> nameList, String fileName) {
    return compress(type, path, nameList, fileName, 0L);
  }

  public static boolean compress(
      CompressTypeEnum type,
      String path,
      List<String> nameList,
      String fileName,
      Long splitSizeMB) {
    Compressor compressor = compressors.get(type.getKey());
    return compressor.compress(path, nameList, fileName, splitSizeMB);
  }

  public static boolean decompress(
      CompressTypeEnum type, String path, String fileName, String destDir) {
    return decompress(type, path, fileName, destDir, null, null);
  }

  public static boolean decompress(
      CompressTypeEnum type, String path, String fileName, String destDir, String password) {
    return decompress(type, path, fileName, destDir, password, null);
  }

  public static boolean decompress(
      CompressTypeEnum type,
      String path,
      String fileName,
      String destDir,
      String password,
      Charset charset) {
    Compressor compressor = compressors.get(type.getKey());
    return compressor.decompress(path, fileName, destDir, password, charset);
  }
}
