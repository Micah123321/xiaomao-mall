package com.xiaomao6.xiaomaoproduct.web;

import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import com.xiaomao6.xiaomaoproduct.vo.Category2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName IndexController
 * @Description 简介
 * @Author Micah
 * @Date 2021/11/8 14:44
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    @GetMapping({"/", "/index", "/index.html"})
    public String indexPage(Model model) {//Model 直接植入模板中
        List<CategoryEntity> list = categoryService.getLevel1Category();//查询出来一级分类
        model.addAttribute("categorys", list);//插入
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Category2Vo>> getCategory2List() {
        Map<String, List<Category2Vo>> map = categoryService.getCategory2List();
        return map;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        //获得锁
        RLock lock = redisson.getLock("my-lock");
//        lock.lock();//这里存在着自动续期机制,具体代码是 如果指定了时间就不使用自动续期机制,存在提取释放锁的情况
        //所以尽量将过期时间往大了调
        lock.lock(20, TimeUnit.SECONDS);
        try {
            System.out.println("执行业务 所需20S,占用了" + Thread.currentThread().getId());
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放了锁");
            lock.unlock();
        }
        return "hello";
    }

    @GetMapping("/write")
    @ResponseBody
    //读写锁 当写锁占用的时候,读锁也会等待 写锁释放的时候,读锁跟着释放
    // 在一直写的情况下 保证能读到最新的数据
    public String write() {

        //获得锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.writeLock();
        rLock.lock(20, TimeUnit.SECONDS);
        String s = UUID.randomUUID().toString();
        try {
            System.out.println("写锁 所需20S,占用了" + Thread.currentThread().getId());
            redisTemplate.opsForValue().set("writeValue", s);
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放了锁");
            rLock.unlock();
        }
        return s;
    }


    @GetMapping("/read")
    @ResponseBody
    public String read() {

        //获得锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.readLock();
        rLock.lock();
        String writeValue = "";
        try {
            writeValue = redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放了锁");
            rLock.unlock();
        }
        return writeValue;
    }

    @GetMapping("/pack")
    @ResponseBody
    public String pack() throws InterruptedException {
        RSemaphore pack = redisson.getSemaphore("pack");
        pack.acquire();
        return "ok";
    }

    @GetMapping("/trypack")
    @ResponseBody
    public String tryPack(){
        RSemaphore pack = redisson.getSemaphore("pack");
        boolean b = pack.tryAcquire();
        return "ok=>"+b;
    }
    @GetMapping("/go")
    @ResponseBody
    public String go() {
        RSemaphore pack = redisson.getSemaphore("pack");
        pack.release();
        return "ok";
    }



    @GetMapping("/lockdoor")
    @ResponseBody
    public String lockdoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5L);
        door.await();
        return "放假了,锁门了";
    }

    @GetMapping("/gogogo")
    @ResponseBody
    public String gogogo() {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();
        return "溜了溜了";
    }

}
