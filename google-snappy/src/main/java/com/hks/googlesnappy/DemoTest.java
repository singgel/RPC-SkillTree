package com.hks.googlesnappy;

import java.util.Arrays;
import org.xerial.snappy.Snappy;
/**
 * @Author: hekuangsheng
 * @Date: 2018/10/29
 *
 * Snappy是Google开源的压缩/解压缩库。
 * 和其他压缩库相比，snappy的压缩率并不是最高的，兼容性也并非最好的。
 * 相反，它的诞生旨在以极高的压缩/解压缩速率提供合理的压缩率。
 */
public class DemoTest {

    public static void main(String[] args) {
        try{
            String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";
            {
                byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
                byte[] uncompressed = Snappy.uncompress(compressed);
                String result = new String(uncompressed, "UTF-8");
                System.out.println(result);
            }

            {
                byte[] compressed = Snappy.compress(input);
                System.out.println(Snappy.uncompressString(compressed));
            }

            {
                double [] arr = new double[]{123.456,234.567,345.678};
                byte[] compressed = Snappy.compress(arr);
                double [] unarr = Snappy.uncompressDoubleArray(compressed);
                System.out.println(Arrays.toString(unarr));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
