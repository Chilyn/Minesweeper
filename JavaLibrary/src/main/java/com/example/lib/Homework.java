package com.example.lib;

import java.util.Arrays;

public class Homework {

    public static void main(String[] args) {
        int[][] square09 = randomSquare09(3);
        for (int i = 0; i < square09.length; i++) {
            System.out.println(Arrays.toString(square09[i]));
        }

        System.out.println("标记的数组：");
        int[][] markedSquare = markedSquare(square09);
        for (int i = 0; i < markedSquare.length; i++) {
            System.out.println(Arrays.toString(markedSquare[i]));
        }
    }

    public static int random01() {
        /*
        作业1
        Java 标准数学库有一个随机数函数
        Math.random()
        它返回 0 - 1 之间的小数

        用它实现本函数, 返回 0 或 1

        提示：
            这道题目有多种实现方式，我们这里拿 Math.random() 与 0.5 比较

        分步提示：
            1. 如果 Math.random() > 0.5，返回 1
            2. 否则返回 0
        */
        return Math.random() > 0.5 ? 1 : 0;
    }

    public static int[] randomLine01(int arrayLength) {
        /*
        作业2
        返回一个只包含了 0 1 的随机 array, 长度为 n
        假设 n 为 5, 返回的数据格式如下(这是格式范例, 真实数据是随机的)
        [0, 0, 1, 0, 1]

        提示：
            循环 n 次，每次调用 random01，push 到数组中

        分步提示：
            1. 循环 n 次，每次调用 random01，把结果 push 到数组中
            2. 返回数组
        */
        int[] arrayRandom01 = new int[arrayLength];
        for (int i = 0; i < arrayRandom01.length; i++) {
            arrayRandom01[i] = random01();
        }

        return arrayRandom01;
    }

    public static int[][] randomSquare01(int length) {
        /*
        作业3
        返回以下格式的数据
        假设 n 为 3, 返回的数据格式如下(这是格式范例, 真实数据是随机的)
        注意, 这只是一个 array, 并不是它显示的样子
        注意, 这是一个 array 不是 string
        [
            [0, 0, 1],
            [1, 0, 1],
            [0, 0, 0],
        ]
        返回, 包含了 n 个『只包含 n 个「随机 0 1」的 array』的 array

        提示：
            循环 n 次，每次调用 randomLine01，把结果 push 到数组中

        分步提示：
            1. 循环 n 次，每次调用 randomLine01，把结果 push 到数组中
            2. 返回数组
        */
        int[][] array = new int[length][];
        for (int i = 0; i < array.length; i++) {
            array[i] = randomLine01(length);
        }

        return array;
    }

    public static int[] randomLine09(int length) {
        /*
        作业4
        返回一个只包含了 0 9 的随机 array, 长度为 n
        假设 n 为 5, 返回的数据格式如下(这是格式范例, 真实数据是随机的)
        [0, 0, 9, 0, 9]

        提示：
            先生成只包含 0 1 的数组，然后把数组里的 1 替换成 9

        分步提示：
            1. 调用函数 randomLine01 得到一个只包含 0 1 的数组 line
            2. 遍历这个数组，如果遍历出来的元素为 1，就把这个位置的元素值设置为 9
            3. 返回 line
        */
        int[] line = randomLine01(length);
        for (int i = 0; i < line.length; i++) {
            if (line[i] == 1) {
                line[i] = 9;
            }
        }

        return line;
    }

    public static int[] markedLine(int[] arrayLine09) {
        /*
        作业5
        array 是一个只包含了 0 9 的 array
        返回一个标记过的 array
        ** 注意, 使用一个新数组来存储结果, 不要直接修改老数组
        复制数组用 array.slice(0) 实现

        标记规则如下
        对于下面这样的 array
        [0, 0, 9, 0, 9]
        标记后是这样
        [0, 1, 9, 2, 9]

        规则是, 0 会被设置为左右两边 9 的数量

        提示：
            把 9 左右加 1，注意判断 9 是否在两边

        分步提示：
            1. 先使用 array.slice(0) 复制数组 array，用变量 line 存储
            2. 遍历数组 line，每次遍历的元素是 n
            3. 如果 n 为 9，并且 n 不是第一个元素，并且 n 左边的数字不是 9，把 n 左边的数字 + 1
            4. 如果 n 为 9，并且 n 不是最后一个元素，并且 n 右边的数字不是 9，把 n 右面的数字 + 1
            5. 返回数组 line
        */
        int[] line = Arrays.copyOf(arrayLine09, arrayLine09.length);
        for (int i = 0; i < line.length; i++) {
            int number = line[i];
            if (number == 9 && i != 0 && line[i - 1] != 9) {
                line[i - 1] = line[i - 1] + 1;
            }

            if (number == 9 && i != (line.length - 1) && line[i + 1] != 9) {
                line[i + 1] = line[i + 1] + 1;
            }
        }
        return line;
    }

    public static int[][] randomSquare09(int length) {
        int[][] array = new int[length][];
        for (int i = 0; i < array.length; i++) {
            array[i] = randomLine09(length);
        }

        return array;
    }

    public static int[][] markedSquare(int[][] array) {
        /*
        作业6
        array 是一个「包含了『只包含了 0 9 的 array』的 array」
        返回一个标记过的 array
        ** 注意, 使用一个新数组来存储结果, 不要直接修改老数组

        范例如下, 这是 array
        [
            [0, 9, 0, 0],
            [0, 0, 9, 0],
            [9, 0, 9, 0],
            [0, 9, 0, 0],
        ]

        这是标记后的结果
        [
            [1, 9, 2, 1],
            [2, 4, 9, 2],
            [9, 4, 9, 2],
            [2, 9, 2, 1],
        ]

        规则是, 0 会被设置为四周 8 个元素中 9 的数量

        提示：
            这道题比较麻烦, 你要是不会, 就直接写「这道题目我不会」
            这道题目循环调用作业 14 的 markedLine，这道题目不要求写测试

        分步提示：
            1. 先定义一个 clonedSquare 函数，把 array 的内容复制到一个新数组中
            2. 调用 clonedSquare 函数，得到 square
            3. 遍历 square，每次遍历的元素为 line
            4. 遍历 line，调用一个 markAround 函数，传入 square, i, j
            5. 实现 markAround 函数，对于每一个 square[i][j] 这样的元素都按照规则 +1
                分 4 个顶角、4 条边和剩下的元素这几种情形
            6. 两重遍历结束后，square 就是需要的结果，return square 即可。
        */
        int[][] square = cloneSquare(array);
        for (int i = 0; i < square.length; i++) {
            int[] line = square[i];
            for (int j = 0; j < line.length; j++) {
                markAround(square, i, j);
            }
        }
        return square;
    }

    public static int[][] cloneSquare(int[][] array) {
        int[][] cloneArray = new int[array.length][];
        for (int i = 0; i < cloneArray.length; i++) {
            int[] line = array[i];
            int[] cloneLine = new int[line.length];
            for (int j = 0; j < cloneLine.length; j++) {
                cloneLine[j] = line[j];
            }

            cloneArray[i] = cloneLine;
        }

        return cloneArray;
    }

    public static void markAround(int[][] square, int row, int column) {
        int number = square[row][column];
        if (number != 9) {
            return;
        }

        /*
            1. 如果 n 为 9，并且 n 不是列第一个元素，并且 n 左边的数字不是 9，把 n 左边的数字 + 1
            2. 如果 n 为 9，并且 n 不是列最后一个元素，并且 n 右边的数字不是 9，把 n 右边的数字 + 1
        */
        if (column != 0 && square[row][column - 1] != 9) {
            square[row][column - 1] += 1;
        }

        if (column != (square[row].length - 1) && square[row][column + 1] != 9) {
            square[row][column + 1] += 1;
        }

        /*
            3. 如果 n 为 9，并且 n 不是行第一个元素，并且 n 上边的数字不是 9，把 n 上边的数字 + 1
            4. 如果 n 为 9，并且 n 不是行最后一个元素，并且 n 下边的数字不是 9，把 n 下边的数字 + 1
        */
        if (row != 0 && square[row - 1][column] != 9) {
            square[row - 1][column] += 1;
        }

        if (row != (square.length - 1) && square[row + 1][column] != 9) {
            square[row + 1][column] += 1;
        }

        /*
            5. 如果 n 为 9，并且 n 不是行或列的第一个元素，并且 n 左上边的数字不是 9，把 n 左上边的数字 + 1
            6. 如果 n 为 9，并且 n 不是行第一个也不是列最后一个元素，并且 n 右上边的数字不是 9，把 n 右上边的数字 + 1
        */
        if (row != 0 && column != 0 && square[row - 1][column - 1] != 9) {
            square[row - 1][column - 1] += 1;
        }

        if (row != 0 && column != (square[row].length - 1) && square[row - 1][column + 1] != 9) {
            square[row - 1][column + 1] += 1;
        }

        /*
            7. 如果 n 为 9，并且 n 不是行第一个也不是列最后一个元素，并且 n 左下边的数字不是 9，把 n 左下边的数字 + 1
            8. 如果 n 为 9，并且 n 不是行或列的最后一个元素，并且 n 右下边的数字不是 9，把 n 右下边的数字 + 1
        */
        if (row != (square.length -1) && column != 0 && square[row + 1][column - 1] != 9) {
            square[row + 1][column - 1] += 1;
        }

        if (row != (square.length -1) && column != (square[row].length - 1) && square[row + 1][column + 1] != 9) {
            square[row + 1][column + 1] += 1;
        }
    }
}
