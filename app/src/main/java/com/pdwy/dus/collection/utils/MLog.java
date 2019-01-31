package com.pdwy.dus.collection.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pdwy.dus.collection.utils.helper.DebugHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 日志输出控制(利用策略模式,控制日志的输出)
 * Author： by MR on 2017/3/15.
 */
public class MLog {
    public static void v(@NonNull String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    public static void v(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    public static void v(Throwable t) {
        TREE_OF_SOULS.v(t);
    }

    public static void d(@NonNull String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    public static void d(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    public static void d(Throwable t) {
        TREE_OF_SOULS.d(t);
    }

    public static void i(@NonNull String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    public static void i(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    public static void i(Throwable t) {
        TREE_OF_SOULS.i(t);
    }

    public static void w(@NonNull String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    public static void w(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.w(t, message, args);
    }

    public static void w(Throwable t) {
        TREE_OF_SOULS.w(t);
    }

    public static void e(@NonNull String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    public static void e(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    public static void e(Throwable t) {
        TREE_OF_SOULS.e(t);
    }

    public static void wtf(@NonNull String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    public static void wtf(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.wtf(t, message, args);
    }

    public static void wtf(Throwable t) {
        TREE_OF_SOULS.wtf(t);
    }

    public static void log(int priority, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, message, args);
    }

    public static void log(int priority, Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, t, message, args);
    }

    public static void log(int priority, Throwable t) {
        TREE_OF_SOULS.log(priority, t);
    }

    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    public static Tree tag(String tag) {
        Tree[] forest = forestAsArray;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].explicitTag.set(tag);
        }
        return TREE_OF_SOULS;
    }

    public static void plant(Tree tree) {
        if (tree == null) {
            DebugHelper.throwNullPointer("tree == null");
            return;
        }
        if (tree == TREE_OF_SOULS) {
            DebugHelper.throwIllegalArgument("Cannot plant Timber into itself.");
            return;
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** 添加一个新的 logging trees. */
    public static void plant(Tree... trees) {
        if (trees == null) {
            DebugHelper.throwNullPointer("tree == null");
            return;
        }
        for (Tree tree : trees) {
            if (tree == null) {
                DebugHelper.throwNullPointer("trees contains null");
                return;
            }
            if (tree == TREE_OF_SOULS) {
                DebugHelper.throwIllegalArgument("Cannot plant Timber into itself.");
                return;
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** 移除 planted tree. */
    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                DebugHelper.throwIllegalArgument("Cannot uproot tree which is not planted: " + tree);
                return;
            }
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /** 移除所有 planted trees. */
    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /** Return a copy of all planted {@linkplain Tree trees}. */
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return Collections.unmodifiableList(new ArrayList<Tree>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    // Both fields guarded by 'FOREST'.
    private static final List<Tree> FOREST = new ArrayList<Tree>();
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    private static final Tree TREE_OF_SOULS = new Tree() {
        @Override
        public void v(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(message, args);
            }
        }

        @Override
        public void v(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(t, message, args);
            }
        }

        @Override
        public void v(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(t);
            }
        }

        @Override
        public void d(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(message, args);
            }
        }

        @Override
        public void d(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(t, message, args);
            }
        }

        @Override
        public void d(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(t);
            }
        }

        @Override
        public void i(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(message, args);
            }
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(t, message, args);
            }
        }

        @Override
        public void i(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(t);
            }
        }

        @Override
        public void w(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(message, args);
            }
        }

        @Override
        public void w(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(t, message, args);
            }
        }

        @Override
        public void w(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(t);
            }
        }

        @Override
        public void e(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(message, args);
            }
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(t, message, args);
            }
        }

        @Override
        public void e(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(t);
            }
        }

        @Override
        public void wtf(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(message, args);
            }
        }

        @Override
        public void wtf(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(t, message, args);
            }
        }

        @Override
        public void wtf(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(t);
            }
        }

        @Override
        public void log(int priority, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, message, args);
            }
        }

        @Override
        public void log(int priority, Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t, message, args);
            }
        }

        @Override
        public void log(int priority, Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t);
            }
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }
    };

    private MLog() {
        throw new AssertionError("No instances.");
    }

    public static abstract class Tree {
        protected static final int CALL_STACK_INDEX = 4;
        final ThreadLocal<String> explicitTag = new ThreadLocal<String>();

        protected String callLine(StackTraceElement[] caller) {
            if (caller.length < CALL_STACK_INDEX) {
                return "";
            }

            String result = "(" + caller[CALL_STACK_INDEX].getFileName()
                    + ":" + caller[CALL_STACK_INDEX].getLineNumber() + ")";
            return result;
        }

        String getTag(StackTraceElement[] stackTrace) {
            String tag = explicitTag.get();
            if (tag != null) {
                explicitTag.remove();
            }
            return tag;
        }

        public void v(String message, Object... args) {
            prepareLog(Log.VERBOSE, null, message, args);
        }

        public void v(Throwable t, String message, Object... args) {
            prepareLog(Log.VERBOSE, t, message, args);
        }

        public void v(Throwable t) {
            prepareLog(Log.VERBOSE, t, null);
        }

        public void d(String message, Object... args) {
            prepareLog(Log.DEBUG, null, message, args);
        }

        public void d(Throwable t, String message, Object... args) {
            prepareLog(Log.DEBUG, t, message, args);
        }

        public void d(Throwable t) {
            prepareLog(Log.DEBUG, t, null);
        }

        public void i(String message, Object... args) {
            prepareLog(Log.INFO, null, message, args);
        }

        public void i(Throwable t, String message, Object... args) {
            prepareLog(Log.INFO, t, message, args);
        }

        public void i(Throwable t) {
            prepareLog(Log.INFO, t, null);
        }

        public void w(String message, Object... args) {
            prepareLog(Log.WARN, null, message, args);
        }

        public void w(Throwable t, String message, Object... args) {
            prepareLog(Log.WARN, t, message, args);
        }

        public void w(Throwable t) {
            prepareLog(Log.WARN, t, null);
        }

        public void e(String message, Object... args) {
            prepareLog(Log.ERROR, null, message, args);
        }

        public void e(Throwable t, String message, Object... args) {
            prepareLog(Log.ERROR, t, message, args);
        }

        public void e(Throwable t) {
            prepareLog(Log.ERROR, t, null);
        }

        public void wtf(String message, Object... args) {
            prepareLog(Log.ASSERT, null, message, args);
        }

        public void wtf(Throwable t, String message, Object... args) {
            prepareLog(Log.ASSERT, t, message, args);
        }

        public void wtf(Throwable t) {
            prepareLog(Log.ASSERT, t, null);
        }

        public void log(int priority, String message, Object... args) {
            prepareLog(priority, null, message, args);
        }

        public void log(int priority, Throwable t, String message, Object... args) {
            prepareLog(priority, t, message, args);
        }

        public void log(int priority, Throwable t) {
            prepareLog(priority, t, null);
        }

        protected boolean isLoggable(int priority) {
            return true;
        }

        private void prepareLog(int priority, Throwable t, String message, Object... args) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            String tag = getTag(stackTrace);

            if (!isLoggable(priority)) {
                return;
            }
            if (message != null && message.length() == 0) {
                message = null;
            }
            if (message == null) {
                if (t == null) {
                    return; // Swallow message if it's null and there's no throwable.
                }
                message = getStackTraceString(t);
            } else {
                if (args.length > 0) {
                    message = String.format(message, args);
                }
                if (t != null) {
                    message += "\n" + getStackTraceString(t);
                }
            }

            message = callLine(stackTrace) + message;
            log(priority, tag, message, t);
        }

        private String getStackTraceString(Throwable t) {
            // Don't replace this with MLog.getStackTraceString() - it hides
            // UnknownHostException, which is not what we want.
            StringWriter sw = new StringWriter(256);
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }

        protected abstract void log(int priority, String tag, String message, Throwable t);
    }

    public static class DebugTree extends Tree {
        private static final int MAX_LOG_LENGTH = 4000;
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

        protected String createStackElementTag(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf('.') + 1);
        }

        @Override
        final String getTag(StackTraceElement[] stackTrace) {
            String tag = super.getTag(stackTrace);
            if (tag != null) {
                return tag;
            }
            if (stackTrace.length <= CALL_STACK_INDEX) {
                return "错误Tag";
            }
//            return callMethodAndLine(stackTrace[CALL_STACK_INDEX]);
            return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into MLog's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }

    public static class ReleaseTree extends Tree {
        @Override
        String getTag(StackTraceElement[] stackTrace) {
            return super.getTag(stackTrace);
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            //只处理大于Info的
            if (priority > Log.INFO)
                logProxy(priority, tag, message, t);
        }

        private void logProxy(int priority, String tag, String message, Throwable t) {
//            switch (priority) {
//                case android.util.MLog.INFO:
//                    BuglyLog.i(tag, message + t);
//                    break;
//                case android.util.MLog.VERBOSE:
//                    BuglyLog.v(tag, message + t);
//                    break;
//                case android.util.MLog.DEBUG:
//                    BuglyLog.d(tag, message + t);
//                    break;
//                case android.util.MLog.WARN:
//                    BuglyLog.w(tag, message + t);
//                    break;
//                case android.util.MLog.ERROR:
//                    BuglyLog.e(tag, message, t);
//                    break;
//                case android.util.MLog.ASSERT:
//                    BuglyLog.w(tag, message + t);
//                    break;
//            }
        }
    }

    /**
     * 空实现
     * create by
     */
    public static class NothingLog extends Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            //do nothing
        }
    }

}
