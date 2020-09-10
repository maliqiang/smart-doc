package com.power.doc.utils;

import com.power.doc.model.ApiParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yu 2020/8/8.
 */
public class ApiParamTreeUtil {

    public static List<ApiParam> apiParamToTree(List<ApiParam> apiParamList) {
        if (Objects.isNull(apiParamList)) {
            return null;
        }
        List<ApiParam> params = new ArrayList<>();
        // find root
        for (ApiParam apiParam : apiParamList) {
            //去除filed的前缀
            apiParam.setField(apiParam.getField().replaceAll("└─", "").replaceAll("&nbsp;", ""));
            // pid == 0
            if (apiParam.getPid() == 0) {
                params.add(apiParam);
            }
        }
        for (ApiParam apiParam : params) {
            //去除filed的前缀
            apiParam.setChildren(getChild(apiParam.getId(), apiParamList));
        }
        return params;
    }

    /**
     * find child
     *
     * @param id           param id
     * @param apiParamList List of ApiParam
     * @return List of ApiParam
     */
    private static List<ApiParam> getChild(int id, List<ApiParam> apiParamList) {
        List<ApiParam> childList = new ArrayList<>();
        for (ApiParam param : apiParamList) {
            if (param.getPid() == id) {
                childList.add(param);
            }
        }
        for (ApiParam param : childList) {
            param.setChildren(getChild(param.getId(), apiParamList));
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
