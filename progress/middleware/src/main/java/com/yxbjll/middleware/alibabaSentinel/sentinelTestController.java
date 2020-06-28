package com.yxbjll.middleware.alibabaSentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sentinelTestController {

	@Autowired
	private SentinelService service;

	@GetMapping("/foo")
	public String apiFoo(@RequestParam(required = false) Long t) throws Exception {
		if (t == null) {
			t = System.currentTimeMillis();
		}
		service.test();
		return "success";
//		return service.hello(t);
	}

	@GetMapping("/baz/{name}")
	public String apiBaz(@PathVariable("name") String name) {
		return service.helloAnother(name);
	}
}
