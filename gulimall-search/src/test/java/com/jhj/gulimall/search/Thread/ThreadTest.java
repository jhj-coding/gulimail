package com.jhj.gulimall.search.Thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    //线程池
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        System.out.println("start");
        //异步任务，放到自己的线程池 无返回值
//        CompletableFuture.runAsync(()->{
//            System.out.println("当前线程"+Thread.currentThread().getId());
//            int i=10/2;
//            System.out.println("运行结果"+i);
//        },executor);


        //异步任务，放到自己的线程池 有返回值
//        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor);
//        Integer integer = integerCompletableFuture.get();
//        System.out.println("end"+integer);

        //异步执行 成功后调用其他方法 whenComplete 在一个线程里执行 whenCompleteAsync 在新的线程执行 参数 第一个是结果 第二个是异常
//         CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).whenCompleteAsync((res,exception)->{
//            System.out.println("结果是"+res+"异常是"+exception);
//        });
//        Integer integer = integerCompletableFuture.get();
//        System.out.println("end"+integer);


        //异步执行 失败 异常处理
//        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).whenCompleteAsync((res,exception)->{
//            System.out.println("结果是"+res+"异常是"+exception);
//        }).exceptionally(throwable -> {
//            return 10;
//        });
//        Integer integer = integerCompletableFuture.get();
//        System.out.println("end"+integer);

        //异步执行 执行后的处理 成功 失败都可以
//        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).handle((res,exception)->{
//            System.out.println("结果是"+res+"异常是"+exception);
//            return 10;
//        });
//        Integer integer = integerCompletableFuture.get();
//        System.out.println("end"+integer);


        /**
         * 一个任务完成后执行另一个任务
         * thenApply  获取上一个任务返回结果，返回当前任务的返回结果
         * <p>
         * thenAccept  获取上一个结果 但并不返回结果
         * <p>
         * thenRun 只要上面任务完成就执行 不获取上一个结果
         * ~Async方法 表示在一个新的线程执行 即开启新线程
         **/
//        thenrun 无接受结果无返回值
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).thenRunAsync(()->{
//            System.out.println("任务2启动了");
//        },executor);

//        thenAccept 有接受结果 无返回值
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).thenAcceptAsync((res)->{
//            System.out.println("任务2启动了"+res);
//        },executor);


        //        thenApply 有接受结果 有返回值
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor).exceptionally((throwable)->{
//            System.out.println(throwable);
//            return 1;
//        }).thenApplyAsync((res)->{
//            System.out.println("当前线程" + Thread.currentThread().getId());
//            System.out.println("任务2启动了"+res);
//            return res;
//        },executor);

        /**
         *两任务 组合 都要完成 完成后执行第三个任务
         *thencombine 组合两个异步 获取两个结果 并返回当前任务的返回值
         *
         *thenacceptboth 组合两个异步 获取两个结果 不返回当前任务的返回值
         *
         *runafterboth 组合两个异步 不获取两个结果 当两个异步处理完之后处理任务
         * astnc 开启新的线程
         **/
//        CompletableFuture<Integer> integerCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前1线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor);
//
//        CompletableFuture<String> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(2);
//            return "hello";
//        }, executor);
//
//        integerCompletableFuture1.runAfterBoth(integerCompletableFuture2,()->{
//            System.out.println(3);
//        },executor);
//
//        integerCompletableFuture1.thenAcceptBothAsync(integerCompletableFuture2,(f1,f2)->{
//            System.out.println(f1+f2);
//        },executor);
//
//        CompletableFuture<String> stringCompletableFuture = integerCompletableFuture1.thenCombineAsync(integerCompletableFuture2, (f1, f2) -> {
//            System.out.println(f1 + f2);
//            return f1 + f2;
//        }, executor);
//
//        System.out.println(stringCompletableFuture.get() );

        /**
         *
         * 两个任务 完成一个就执行
         * applytoEither 获取结果，返回结果
         * acceptEither 获取结果，不返回结果
         * afterEither 不获取结果，不返回结果
         *
         *
         *
         *
         *
         **/

//        CompletableFuture<Object> integerCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前1线程" + Thread.currentThread().getId());
//            int i = 10 / 2;
////            try {
////                Thread.sleep(1000l);
////            } catch (InterruptedException e) {
////                throw new RuntimeException(e);
////            }
//            System.out.println("运行结果" + i);
//            return i;
//        }, executor);
//
//        CompletableFuture<Object> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(2);
//            return "hello";
//        }, executor);
//
//        integerCompletableFuture1.runAfterEitherAsync(integerCompletableFuture2,
//                () -> {
//                    System.out.println(3);
//                }, executor);
//
//        integerCompletableFuture1.acceptEitherAsync(integerCompletableFuture2,
//                (res) -> {
//                    System.out.println("3"+res);
//                }, executor);
//
//        integerCompletableFuture1.applyToEitherAsync(integerCompletableFuture2,
//                (res) -> {
//                    System.out.println("3"+res);
//                    return "res";
//                }, executor);


        /**
         *
         * 多任务
         * allof 全部都完成
         *
         * anyof 一个完成都行
         *
         *
         *
         *
         *
         **/
        CompletableFuture<String> 查询图片信息 = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询图片信息");
            return "hello.jpg";
        },executor);

        CompletableFuture<String> 查询属性信息 = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询属性信息");
            return "黑色+256G";
        },executor);

        CompletableFuture<String> 华为 = CompletableFuture.supplyAsync(() -> {
            System.out.println("华为");
            return "华为";
        },executor);

//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(查询图片信息, 查询属性信息, 华为);
//        //等待所有做完
//        voidCompletableFuture.get();
//        System.out.println("1");


        //有一个做完就 返回第一个做完的
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(查询图片信息, 查询属性信息, 华为);
        objectCompletableFuture.get();
        System.out.println(objectCompletableFuture.get());
    }
}
