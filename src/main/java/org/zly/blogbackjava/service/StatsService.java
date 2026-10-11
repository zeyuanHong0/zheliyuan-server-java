package org.zly.blogbackjava.service;

import org.zly.blogbackjava.entity.User;

import java.util.List;
import java.util.Map;

public interface StatsService {

    Map<String, Object> getDashboardStats(User user);

    List<Integer> getBlogStats();
}
