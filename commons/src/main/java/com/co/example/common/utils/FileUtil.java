package com.co.example.common.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtil {
  /**
   * 私有构造方法，防止类的实例化。
   */
  private FileUtil() {
  }

  /**
   * 创建文件
   * @param file
   */
  public static void touch(File file) {
    long currentTime = System.currentTimeMillis();
    if (!file.exists()) {
      System.err.println("file not found:" + file.getName());
      System.err.println("Create a new file:" + file.getName());
      makeDirectory(file);
      try {
        if (file.createNewFile()) {
          System.out.println("Succeeded!");
        }else {
          System.err.println("Create file failed!");
        }
      }
      catch (IOException e) {
        System.err.println("Create file failed!");
        e.printStackTrace();
      }
    }
    boolean result = file.setLastModified(currentTime);
    if (!result) {
      System.err.println("touch failed: " + file.getName());
    }
  }

   public static void touch(String fileName) {
    File file = new File(fileName);
    touch(file);
  }

   
  /**
   * 修改文件的最后访问时间。
   * 如果文件不存在则创建该文件。
   * @param files 需要修改最后访问时间的文件数组。
   */
  public static void touch(File[] files) {
    for (int i = 0; i < files.length; i++) {
      touch(files[i]);
    }
  }

  /**
   * 修改文件的最后访问时间。
   * 如果文件不存在则创建该文件。
   * @param fileNames 需要修改最后访问时间的文件名数组。
   */
  public static void touch(String[] fileNames) {
    File[] files = new File[fileNames.length];
    for (int i = 0; i < fileNames.length; i++) {
      files[i] = new File(fileNames[i]);
    }
    touch(files);
  }

  /**
   * 判断指定的文件是否存在。
   * @param fileName 要判断的文件的文件名
   * @return 存在时返回true，否则返回false。
   */
  public static boolean isFileExist(String fileName) {
    return new File(fileName).isFile();
  }

  /**
   * 创建指定的目录。
   * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
   * <b>注意：可能会在返回false的时候创建部分父目录。</b>
   * @param file 要创建的目录
   * @return 完全创建成功时返回true，否则返回false。
   */
  public static boolean makeDirectory(File file) {
    File parent = file.getParentFile();
    if (parent != null) {
      return parent.mkdirs();
    }
    return false;
  }

  /**
   * 创建指定的目录。
   * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
   * <b>注意：可能会在返回false的时候创建部分父目录。</b>
   * @param fileName 要创建的目录的目录名
   * @return 完全创建成功时返回true，否则返回false。
   */
  public static boolean makeDirectory(String fileName) {
    File file = new File(fileName);
    return makeDirectory(file);
  }

  /**
   * 清空指定目录中的文件。
   * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
   * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
   * @param directory 要清空的目录
   * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
   */
  public static boolean emptyDirectory(File directory) {
    boolean result = true;
    File[] entries = directory.listFiles();
    for (int i = 0; i < entries.length; i++) {
      if (!entries[i].delete()) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 清空指定目录中的文件。
   * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
   * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
   * @param directoryName 要清空的目录的目录名
   * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
   */
  public static boolean emptyDirectory(String directoryName) {
    File dir = new File(directoryName);
    return emptyDirectory(dir);
  }

  /**
   * 删除指定目录及其中的所有内容。
   * @param dirName 要删除的目录的目录名
   * @return 删除成功时返回true，否则返回false。
   */
  public static boolean deleteDirectory(String dirName) {
    return deleteDirectory(new File(dirName));
  }

  /**
   * 删除指定目录及其中的所有内容。
   * @param dir 要删除的目录
   * @return 删除成功时返回true，否则返回false。
   */
  public static boolean deleteDirectory(File dir) {
    if ( (dir == null) || !dir.isDirectory()) {
      throw new IllegalArgumentException("不是一个目录 ");
    }
    File[] entries = dir.listFiles();
    int sz = entries.length;
    for (int i = 0; i < sz; i++) {
      if (entries[i].isDirectory()) {
        if (!deleteDirectory(entries[i])) {
          return false;
        }
      }else {
        if (!entries[i].delete()) {
          return false;
        }
      }
    }
    if (!dir.delete()) {
      return false;
    }
    return true;
  }

 

  /**
   * 从文件路径得到文件名。
   * @param filePath 文件的路径，可以是相对路径也可以是绝对路径
   * @return 对应的文件名
   */
  public static String getFileName(String filePath) {
    File file = new File(filePath);
    return file.getName();
  }

  /**
   * 从文件名得到文件绝对路径。
   * @param fileName 文件名
   * @return 对应的文件路径
   */
  public static String getFilePath(String fileName) {
    File file = new File(fileName);
    return file.getAbsolutePath();
  }

 
  /**
   * 得到文件的后缀名。
   * 实际上就是得到文件名中最后一个“.”后面的部分。
   * @param fileName 文件名
   * @return 文件名中的类型部分
   */
  public static String getFileType(String fileName) {
    int point = fileName.lastIndexOf('.');
    if (point == -1 || point == fileName.length() - 1) {
      return null;
    }else {
      return fileName.substring(point + 1, fileName.length());
    }
  }



  /**
   * 将文件名中的类型部分去掉。
   * @param fileName 文件名
   * @return 去掉类型部分的结果
   */
  public static String trimType(String fileName) {
    int index = fileName.lastIndexOf(".");
    if (index != -1) {
      return fileName.substring(0, index);
    }
    else {
      return fileName;
    }
  }
  
  
   /**
    * 读取文件的内容
    * 读取指定文件的内容
    * @param path 为要读取文件的绝对路径
    * @return 以行读取文件后的内容。
    */
   public static final String getFileContent(String path) throws IOException
   {
     String filecontent = "";
     try {
       File f = new File(path);
       if (f.exists()) {
         FileReader fr = new FileReader(path);
         BufferedReader br = new BufferedReader(fr); //建立BufferedReader对象，并实例化为br
         String line = br.readLine(); //从文件读取一行字符串
         //判断读取到的字符串是否不为空
         while (line != null) {
           filecontent += line + "\n";
           line = br.readLine(); //从文件中继续读取一行数据
         }
         br.close(); //关闭BufferedReader对象
         fr.close(); //关闭文件
       }

     }
     catch (IOException e) {
       throw e;
     }
     return filecontent;
   }


   /**
    * copy文件
    * @param in
    * @param out
    * @return
    * @throws Exception
    */
   public static final boolean copy(File in, File out) throws Exception {
       try {
           FileInputStream fis = new FileInputStream(in);
           FileOutputStream fos = new FileOutputStream(out);
           byte[] buf = new byte[1024];
           int i = 0;
           while ((i = fis.read(buf)) != -1) {
               fos.write(buf, 0, i);
           }
           fis.close();
           fos.close();
           return true;
       } catch (IOException ie) {
           ie.printStackTrace();
           return false;
       }
   }
   
   /**
    * 根据文件名称copy文件
    * @param inFile
    * @param outFile
    * @return
    * @throws Exception
    */
   public static final boolean copy(String inFile, String outFile) throws Exception {
       try {
           File in = new File(inFile);
           File out = new File(outFile);
           return copy(in, out);
       } catch (IOException ie) {
           ie.printStackTrace();
           return false;
       }
   }
   
   /**
    * 根据数据流copy文件，流拷贝参见org.apache.commons.io.IOUtils
    * @param inFile
    * @param outFile
    * @return
    * @throws Exception
    * @see org.apache.commons.io.IOUtils
    */
   public static final boolean copy(InputStream input, OutputStream output) throws Exception {
	   try {
           byte[] buf = new byte[1024];
           int i = 0;
           while ((i = input.read(buf)) != -1) {
        	   output.write(buf, 0, i);
           }
//         org.apache.commons.io.IOUtils.copy(input, output);
           input.close();
           output.close();
           return true;
	   } catch (IOException ie) {
		   ie.printStackTrace();
		   return false;
	   }
   }
   

}
