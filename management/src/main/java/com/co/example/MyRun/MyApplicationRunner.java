package com.co.example.MyRun;

import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.co.example.common.constant.Constant;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.aide.TBrManifestQuery;
import com.co.example.service.manifest.TBrManifestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

	@Inject
	TBrManifestService tBrManifestService;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// TODO Application
		log.info("Run the following methods at server startup!!");
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				DoTask();
//			}
//		}).start();
	}

	public void DoTask() {
		while (true) {
			try {
				log.info("**DoTask**清单任消费者程序执行**start**");
				TBrManifestQuery tBrManifestQuery = new TBrManifestQuery();
				tBrManifestQuery.setStatus(Constant.TASK_STATUS_TODO);
				tBrManifestQuery.setDelFlg(Constant.NO);
				Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "t.create_time");
				List<TBrManifest> mList = tBrManifestService.queryList(tBrManifestQuery, sort);
				if (mList.size() == 0) {
					log.info("**DoTask**暂时没有任务睡眠10分钟**");
					Thread.sleep(600000);
				} else {
					// 从data表读取数据存储到result表中，并更新更新主表的状态
					log.info("**DoTask**有任务待执行**");
					for (TBrManifest tBrManifest : mList) {
						tBrManifestService.queryManifest(tBrManifest);
					}
				}
			} catch (Exception e) {
				log.info("**DoTask**清单人物消费者程序报错**exception**");
				e.printStackTrace();
				try {
					log.info("**DoTask**报错，睡眠10分钟**");
					Thread.sleep(600000);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
