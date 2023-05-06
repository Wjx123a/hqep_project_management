package utils;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.ArrayList;
import java.util.List;


public class PageData2Web {


    public static PageData WebForList(List list) {
        PageData resultPd = new PageData();
        try {
            if (list != null) {
                resultPd.put("data", list);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "无数据返回!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "数据转pageData错误！");
        }
        return resultPd;
    }



    public static PageData WebForPageInfo(PageInfo pageInfo) {
        PageData resultPd = new PageData();
        try {
            if (pageInfo.getList() != null) {
                resultPd.put("data", pageInfo.getList());
                PageData paginationPd = new PageData();
                paginationPd.put("total", pageInfo.getTotal());
                paginationPd.put("pageSize", pageInfo.getPageSize());
                paginationPd.put("current", pageInfo.getPageNum());
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "无数据返回!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "数据转pageData错误！");
        }
        return resultPd;
    }


    public static PageData WebForBoolean(boolean state) {
        PageData resultPd = new PageData();
        try {
            if (state) {
                resultPd.put("data", "success");
                resultPd.put("error", null);
            } else {
                resultPd.put("data", "error");
                resultPd.put("error", "操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", "error");
            resultPd.put("error", "数据转pageData错误！");
        }
        return resultPd;
    }


    public static PageData WebForBooleanObj(boolean state,Object obj) {
        PageData resultPd = new PageData();
        try {
            if (state) {
                resultPd.put("data", obj);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", obj);
                resultPd.put("error", "操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", "error");
            resultPd.put("error", "数据转pageData错误！");
        }
        return resultPd;
    }


    public static PageData WebForObj(Object obj) {
        PageData resultPd = new PageData();
        try {
            if (obj != null) {
                resultPd.put("data", obj);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", null);
                resultPd.put("error", "无数据返回!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "数据转pageData错误！");
        }
        return resultPd;
    }
}
