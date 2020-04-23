// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_VARIABLE -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -UNUSED_VALUE -UNUSED_PARAMETER -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-366
 * PLACE: overload-resolution, choosing-the-most-specific-candidate-from-the-overload-candidate-set, algorithm-of-msc-selection -> paragraph 9 -> sentence 4
 * RELEVANT PLACES: overload-resolution, choosing-the-most-specific-candidate-from-the-overload-candidate-set, algorithm-of-msc-selection -> paragraph 3 -> sentence 1
 * overload-resolution, choosing-the-most-specific-candidate-from-the-overload-candidate-set, algorithm-of-msc-selection -> paragraph 3 -> sentence 2
 * NUMBER: 1
 * DESCRIPTION: call with explicit receiver: different built-in integer types and one of them is kotlin.Int
 */

// TESTCASE NUMBER: 1
class Case1

fun case1(case: Case1) {
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: boo; typeCall: extension function")!>boo(1)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(1)<!>
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: boo; typeCall: extension function")!>boo(x = 1)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(x = 1)<!>

}

fun Case1.boo(x: Int): String = TODO() //(1.1)
fun Case1.boo(x: Long): Unit = TODO() //(1.2)
fun Case1.boo(x: Short): Unit = TODO() //(1.3)
fun Case1.boo(x: Byte): Unit = TODO() //(1.4)

// TESTCASE NUMBER: 2
class Case2 {
    fun boo(x: Int, y: Int): String = TODO() //(1.1)
    fun boo(x: Long, y: Int): Unit = TODO() //(1.2)
    fun boo(x: Short, y: Int): Unit = TODO() //(1.3)
    fun boo(x: Byte, y: Int): Unit = TODO() //(1.4)
}

fun case2(case: Case2) {
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case2.boo; typeCall: function")!>boo(1, 2)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(1, 2)<!>
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case2.boo; typeCall: function")!>boo(x = 1, y = 2)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(x = 1, y = 2)<!>
}

// TESTCASE NUMBER: 3
class Case3 {
    fun boo(x: Int, y: Int): String = TODO() //(1.1)
    fun boo(x: Int, y: Short): Unit = TODO() //(1.2)
}

fun case3(case: Case3) {
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case3.boo; typeCall: function")!>boo(1, 2)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(1, 2)<!>
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case3.boo; typeCall: function")!>boo(x = 1, y = 2)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.boo(x = 1, y = 2)<!>
}

// TESTCASE NUMBER: 4
class Case4 {
    operator fun plus(x: Int): String = TODO() //(1.1)
    operator fun plus(x: Long): Unit = TODO() //(1.2)
    operator fun plus(x: Short): Unit = TODO() //(1.3)
    operator fun plus(x: Byte): Unit = TODO() //(1.4)
}

fun case4(case: Case4) {
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case4.plus; typeCall: operator function")!>plus(1)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.plus(1)<!>
    //to (1.1)
    case.<!DEBUG_INFO_CALL("fqName: Case4.plus; typeCall: operator function")!>plus(x = 1)<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case.plus(x = 1)<!>
    //as operator call case + 1
    //to (1.1)
    <!DEBUG_INFO_CALL("fqName: Case4.plus; typeCall: operator function")!>case+1<!>
    //(1.1) return type is String
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>case+1<!>
}

// TESTCASE NUMBER: 5
class Case5 {
    fun List<Int>.foo(x: Int = 0): String = TODO()
    fun List<Int>.foo(x: Short = 0): Unit = TODO()
    fun List<Byte>.foo(x: Byte = 0): Unit = TODO()

    fun case(list: List<Int>) {
        list.<!DEBUG_INFO_CALL("")!>foo(1)<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>list.foo(1)<!>
    }
}

// TESTCASE NUMBER: 6
class Case6 {
    fun List<Int>.foo(x: Int, y: Int = 1): String = TODO()

    fun <T> List<Int>.foo(x: T): Unit = TODO()

    fun case(list: List<Int>) {
        list.<!DEBUG_INFO_CALL("")!>foo(1)<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>list.foo(1)<!>
    }
}

// TESTCASE NUMBER: 7
class Case7 {
    fun <T> List<T>.foo(x: T, y: Int): String = TODO()

    fun <T> List<T>.foo(x: T, y: Short): Unit = TODO()

    fun case(list: List<Short>) {
        list.<!DEBUG_INFO_CALL("")!>foo(1, 1)<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>list.foo(1, 1)<!>
    }
}
