package com.example.gladoscheckin.common;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

//@Configuration
public class ExecutorConfig {


    /**
     * 得到cpu 的核数
     */
    private static int processorsCount = Runtime.getRuntime().availableProcessors();



    @Bean
    public ExecutorService executorService(){

        ExecutorService executorService = new ThreadPoolExecutor(processorsCount + 1, 100,
                10L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        return executorService;

    }

    /**
     * 延迟的线程池，对那些网络延迟的数据，比如sql查询的时候 这里我们使用无界队列，保障这个线程池不会拒绝请求
     * @return
     */
    @Bean
    public ExecutorService waitExecutorService(){

        ExecutorService waitExecutorService = new ThreadPoolExecutor(processorsCount * 20 , processorsCount * 40,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        return waitExecutorService;

    }

    /**
     * 任务执行队列，因为这个和服务器的计算有关系，所以我们这里不能纵容它有太多的正在运行，并且，我们要无界，而其要有序，不能无序。依然使用有序的无界队列
     * 并且不能增加， 这里其实就是一个固定的线程池
     * @return
     */
    @Bean
    public ExecutorService workExecutorService(){
      /*  Executors.newFixedThreadPool(5);

        new ThreadPoolExecutor(processorsCount + 1, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());*/
        ExecutorService workExecutorService = new ThreadPoolExecutor(processorsCount + 1, processorsCount + 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        return workExecutorService;

    }
}
