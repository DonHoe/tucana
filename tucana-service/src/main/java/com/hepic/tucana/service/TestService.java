package com.hepic.tucana.service;

import com.hepic.tucana.model.authority.SysUser;
import com.hepic.tucana.dal.entity.sqlite.Answer;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
public interface TestService {

    List<Answer> getList(String name);

}
