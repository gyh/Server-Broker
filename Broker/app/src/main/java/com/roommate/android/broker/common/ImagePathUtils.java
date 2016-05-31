package com.roommate.android.broker.common;

/**
 * Created by yepeng on 14-3-13.
 * 图片路径处理工具类
 */
public class ImagePathUtils {

    public final static int SMALLEST = 80;
    public final static int SMALL = 160;
    public final static int NEW_SMALL = 540;
    public final static int MIDDLE = 250;
    public final static int NEW_MIDDLE = 720;
    public final static int NORMAL = 400;

    /**
     * 依据控件大小获取图片路径
     *
     * @param width   图片宽度
     * @param height  图片高度
     * @param isLarge 是否是大图片
     * @param path    图片路径
     * @return
     */
    public static String getImgPath(int width, int height, boolean isLarge, String path) {
        if (width <= 0 && height <= 0 && !isLarge) {//当宽度或高度填充屏幕时
            return ImagePathUtils.getNormalUrl(path);
        } else if (width <= ImagePathUtils.SMALLEST &&
                height <= ImagePathUtils.SMALLEST) {//当宽度与高度小于最小单位时
            return ImagePathUtils.getSmallestUrl(path);
        } else if (width <= ImagePathUtils.SMALL &&
                height <= ImagePathUtils.SMALL) {//当宽度与高度小于小单位时
            return ImagePathUtils.getSmallUrl(path);
        } else if (width <= ImagePathUtils.MIDDLE &&
                height <= ImagePathUtils.MIDDLE) {//当宽度与高度小于中单位时
            return ImagePathUtils.getMiddle(path);
        } else if (width <= ImagePathUtils.NORMAL &&
                height <= ImagePathUtils.NORMAL) {//当宽度与高度小于普通单位时
            return ImagePathUtils.getNormalUrl(path);
        }
        return path;
    }

    /**
     * 获取到列表最小图片的路径
     *
     * @param picurl
     * @return
     */
    public static String getSmallestUrl(String picurl) {
        return getUrl(picurl, SMALLEST);
    }

    /**
     * 获取到列表小图片的路径
     *
     * @param picurl
     * @return
     */
    public static String getSmallUrl(String picurl) {
        return getUrl(picurl, SMALL);
    }

    /**
     * 获取到列表中图片的路径
     *
     * @param picurl
     * @return
     */
    public static String getMiddle(String picurl) {
        return getUrl(picurl, MIDDLE);
    }


    /**
     * 获取到列表中等图片的路径
     *
     * @param picurl
     * @return
     */
    public static String getNormalUrl(String picurl) {
        return getUrl(picurl, NORMAL);
    }

    /**
     * 处理图片路径的核心方法
     *
     * @param picurl
     * @param size
     * @return
     */
    private static String getUrl(String picurl, int size) {

        if (picurl != null) {
            //有可能有的路径已经处理好大小了，需要去掉再次处理
            int errorIndex = picurl.lastIndexOf("_");
            if (errorIndex != -1 && picurl.length() - errorIndex <= 8) {
                picurl = picurl.replace(picurl.substring(errorIndex, picurl.lastIndexOf(".")), "");
            }

            String prefx = picurl.contains(".jpg") ? ".jpg" : (picurl.contains(".png") ? ".png" : "");
            if (picurl.length() > 4 && prefx.length() > 0) {
                int pul = picurl.length();
                picurl = picurl.substring(0, pul - 4) + "_" + size + prefx;
            }
        }
        return picurl;
    }


}
