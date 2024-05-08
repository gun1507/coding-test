package codeTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 연산자 배치하기
 * n개의 정수가 순서대로 주어질 때, n−1개의 연산자를 정수 사이에 하나씩 배치하고자 합니다.
 * 이 때 주어진 정수의 순서를 바꿀 수 없으며, 연산자는 덧셈, 뺄셈, 곰셈 이렇게 세 가지 종류가 있습니다.
 * 연산자 간의 우선 순위를 무시하고 앞에서부터 차례대로 연산한다고 하였을 때, 가능한 식의 최솟값과 최댓값을 출력하는 코드를 작성해보세요.
 *
 * - 입력 형식
 * 첫 번째 줄에는 n이 주어지고, 두 번째 줄에는 n개의 정수가 공백을 사이에 두고 주어집니다. 세 번째 줄에는 사용 가능한 덧셈, 뺄셈, 곱셈의 개수가 순서대로 공백을 사이에 두고 주어집니다. (단, 사용 가능한 모든 연산자 개수의 합은 n−1입니다.)
 * 2≤n≤11
 * 1≤ 입력으로 주어지는 숫자 ≤100
 *
 * - 출력 형식
 * 모든 연산자를 배치했을 때 가능한 식의 최솟값과 최댓값을 공백을 사이에 두고 출력합니다.
 * 계산 도중 그리고 결과 값 역시 −10억이상 10억 사이의 값 만을 갖는다고 가정해도 좋습니다.
 */

public class ArrangeOperator {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("./input/input.txt"));
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 정수 갯수
        int[] number = new int[N];
        int plus, minus, multiple;

        for(int i = 0; i < N; i++) {
            number[i] = sc.nextInt();
        }
        plus = sc.nextInt();
        minus = sc.nextInt();
        multiple = sc.nextInt();



    }
}
