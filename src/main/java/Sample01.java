import javafx.scene.effect.DisplacementMap;

import java.util.Random;
import java.util.Scanner;

/**
 * Основной класс программы
 */
public class Sample01 {

    public static char[][] map; // Массив для игрового поля
    public static final int SIZE = 5; // Размерность игрового поля
    public static final int DOTS_TO_WIN = 4; // Количество знаков для выигрыша
    public static int dotsToWinForAi = DOTS_TO_WIN - 1; // Количество знаков для выигрыша для продвинутого компьютера в отношении человека

    public static final char DOT_EMPTY = '♦'; // Символ пустой клетки
    public static final char DOT_X = 'X'; // Символ игрока
    public static final char DOT_O = 'O'; // Символ компьютера

    public static Scanner scanner = new Scanner(System.in); // Сканер
    public static Random random = new Random(); // Рандом

    /**
     * Инициализация игового поля
     */
    public static void initMap() {

        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }

    }

    /**
     * Вывод игрового поля в консоль
     */
    public static void printMap() {

        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {

            System.out.print((i + 1) + " ");

            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }

            System.out.println();

        }

    }

    /**
     * Ход человека
     */
    public static void humanTurn() {

        int x, y;

        do {
            System.out.println("Введите координаты в формате X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));

        map[y][x] = DOT_X;

    }

    /**
     * Проверка на пустая ли клетка и находиться ли она на поле
     * @param x 1 координата
     * @param y 2 координата
     * @return результат проверки
     */
    public static boolean isCellValid(int x, int y) {

        boolean result = false;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            result = false;

        if (map[y][x] == DOT_EMPTY)
            result = true;

        return result;
    }

    /**
     * Логика хода компьютера
     */
    public static void aiTurn() {

        int x = -1, y = -1;
        boolean winFlag = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j ++) {

                if (isCellValid(j, i)) {

                    map[i][j] = DOT_X;
                    if (!checkDot(DOT_X, dotsToWinForAi))
                        map[i][j] = DOT_EMPTY;

                    if (checkDot(DOT_X, dotsToWinForAi)) {
                        map[i][j] = DOT_EMPTY;
                        x = j;
                        y = i;
                        winFlag = true;
                        break;
                    }
                }
                if (winFlag)
                    break;
            }
            if (winFlag)
                break;
        }
        if (x == -1) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isCellValid(x, y));
        }

        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        map[y][x] = DOT_O;
    }

    /**
     * Метод для проверки условия выигрыша
     * @param symbol Символ игрока для которого проверяем условие выигрыша
     * @param DOTS_TO_WIN значение сколько нужно символов для выигрыша
     * @return возвращаем результат проверки
     */
    public static boolean checkDot(char symbol, int DOTS_TO_WIN) {

        boolean flagToWin = false;
        int test = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
            test = 0;
                if (map[i][j] == symbol) {
                    for (int k = 0; k <= DOTS_TO_WIN - 1; k++) {

                        if (i + k > SIZE - 1) {
                            flagToWin = false;
                            break;
                        }

                        if (map[i + k][j] == symbol) {
                            flagToWin = true;
                            test++;
                        } else {
                            flagToWin = false;
                            break;
                        }

                        if (flagToWin && test == DOTS_TO_WIN)
                            break;
                    }
                    if (flagToWin)
                        break;

                    test = 0;
                    for (int k = 0; k <= DOTS_TO_WIN - 1; k++) {

                        if (j + k > SIZE - 1) {
                            flagToWin = false;
                            break;
                        }

                        if (map[i][j + k] == symbol) {
                            flagToWin = true;
                            test++;
                        } else {
                            flagToWin = false;
                            break;
                        }

                        if (flagToWin && test == DOTS_TO_WIN)
                            break;
                    }
                    if (flagToWin)
                        break;
                    test = 0;
                    for (int k = 0; k <= DOTS_TO_WIN - 1; k++) {

                        if (i + k > SIZE - 1 || j + k > SIZE - 1) {
                            flagToWin = false;
                            break;
                        }

                        if (map[i + k][j + k] == symbol) {
                            flagToWin = true;
                            test++;
                        } else {
                            flagToWin = false;
                            break;
                        }

                        if (flagToWin && test == DOTS_TO_WIN)
                            break;
                    }
                    if (flagToWin)
                        break;

                    test = 0;
                    for (int k = 0; k <= DOTS_TO_WIN - 1; k++) {

                        if (i + k > SIZE - 1 || j - k < 0) {
                            flagToWin = false;
                            break;
                        }

                        if (map[i + k][j - k] == symbol) {
                            flagToWin = true;
                            test++;
                        } else {
                            flagToWin = false;
                            break;
                        }

                        if (flagToWin && test == DOTS_TO_WIN)
                            break;
                    }
                    test = 0;
                }
                if (flagToWin)
                    break;
            }
            if (flagToWin)
                break;
        }
        return flagToWin;
    }

    /**
     * Старый метод проверки выигрыша(для поля 3х3)
     * @param symbol Символ игрока для которого проверяем условия выигрыша
     * @return результат проверки
     */
    public static boolean checkWin(char symbol) {

        if (map[0][0] == symbol && map[0][1] == symbol && map[0][2] == symbol) return true;
        if (map[1][0] == symbol && map[1][1] == symbol && map[1][2] == symbol) return true;
        if (map[2][0] == symbol && map[2][1] == symbol && map[2][2] == symbol) return true;

        if (map[0][0] == symbol && map[1][0] == symbol && map[2][0] == symbol) return true;
        if (map[0][1] == symbol && map[1][1] == symbol && map[2][1] == symbol) return true;
        if (map[0][2] == symbol && map[1][2] == symbol && map[2][2] == symbol) return true;

        if (map[0][0] == symbol && map[1][1] == symbol && map[2][2] == symbol) return true;
        if (map[0][2] == symbol && map[1][1] == symbol && map[2][0] == symbol) return true;

        return false;

    }

    /**
     *  Проверка есть ли на поле пустые клетки
     * @return возвращаем результат проверки
     */
    public static boolean isMapFull() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY)
                    return false;
            }
        }
        return true;
    }


    /**
     * Точка входа в программу
     * @param args
     */
    public static void main(String[] args) {

        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkDot(DOT_X, DOTS_TO_WIN)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkDot(DOT_O, DOTS_TO_WIN)) {
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }

        System.out.println("Игра окончена");

    }




}
