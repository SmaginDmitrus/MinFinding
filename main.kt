import kotlin.math.*
import kotlin.system.exitProcess

fun f(x: Double, y: Double): Double//Для функции двух переменных попробуем применить метод спуска
{
    return ((x * x + y - cos(x + y)).pow(2.0) + sin(y - x).pow(2.0))
}

fun norm(x: Double, y: Double): Double {//поиск квадрата модуля вектора
    return (x * x + y * y)
}

fun goldenratiox(a: Double, b: Double, y0: Double, delta: Double): Double {//метод золотого сечения
    val eta = (sqrt(5.0) - 1) * 0.5
    var A = a
    var B = b
    var counter = 0
    var x2 = (1 - eta) * a + eta * b
    var x1 = eta * a + (1 - eta) * b



    while (abs(A - B) > delta) {
        counter++
        if (f(x2, y0) < f(x1, y0)) {
            A = x1
        } else B = x2
        x1 = eta * A + (1 - eta) * B
        x2 = (1 - eta) * A + eta * B
        if (counter > 1000) exitProcess(1)
    }

    return (A + B) * 0.5
}

fun goldenratioy(a: Double, b: Double, x0: Double, delta: Double): Double {
    val eta = (sqrt(5.0) - 1) * 0.5

    var A = a

    var B = b

    var counter = 0
    var y2 = (1 - eta) * a + eta * b //  Левее чем х2
    var y1 = eta * a + (1 - eta) * b



    while (abs(A - B) > delta) {
        counter++
        if (f(x0, y2) < f(x0, y1)) {
            A = y1
        } else B = y2
        y1 = eta * A + (1 - eta) * B
        y2 = (1 - eta) * A + eta * B

    }

    return (A + B) * 0.5
}


fun powell(x0: Double, y0: Double, delta: Double): Pair<Double, Double> {
    var counter = 0
    var x: Double
    val r = 3.0
    var y: Double
    var x_res = x0
    var y_res = y0
    while (true) {
        counter++
        x = goldenratiox(-r, r, y_res, delta)
        y = goldenratioy(-r, r, x, delta)
        if (abs(norm(x, y) - norm(x_res, y_res)) < delta) {
            break
        } else {
            x_res = x
            y_res = y
        }
        if (counter > 1000) {
            println("$x0,$y0")
            x_res = 0.0
            y_res = 0.0
            break
        }
    }

    return Pair(x_res, y_res)
}

fun main() {
    val delta = 0.00001
    var x = -3.0
    var y = -3.0
    var x_min = 0.0
    var y_min = 0.0
    var min = f(-3.0, -3.0)
    var result: Pair<Double, Double>
    while (x <= 3.0) {
        while (y <= 3.0) {
            result = powell(x, y, delta);
            if (f(result.first, result.second) < min) {
                x_min = result.first
                y_min = result.second
                min = f(result.first, result.second)
            }

            y += 0.1
        }
        y = -3.0
        x += 0.1
    }
    println(powell(-3.0,-3.0,delta))
    println(f(-0.8495018,-0.84950180111))
    x_min = (x_min * 100000).roundToInt() / 100000.0
    y_min = (y_min * 100000).roundToInt() / 100000.0
    min = (min * 100000).roundToInt() / 100000.0
    print("Глобальный минимум в точке ($x_min,$y_min) = $min")

}