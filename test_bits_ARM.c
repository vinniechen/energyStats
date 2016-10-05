/* Testing Code */

#include <limits.h>
#include <math.h>
#include <stdio.h>
#include <assert.h>
#include "bits_ARM.h"

/* Copyright (C) 1991-2014 Free Software Foundation, Inc.
   This file is part of the GNU C Library.

   The GNU C Library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   The GNU C Library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with the GNU C Library; if not, see
   <http://www.gnu.org/licenses/>.  */
/* This header is separate from features.h so that the compiler can
   include it implicitly at the start of every compilation.  It must
   not itself include <features.h> or any other header that includes
   <features.h> because the implicit include comes before any feature
   test macros that may be defined in a source file before it first
   explicitly includes a system header.  GCC knows the name of this
   header in order to preinclude it.  */
/* glibc's intent is to support the IEC 559 math functionality, real
   and complex.  If the GCC (4.9 and later) predefined macros
   specifying compiler intent are available, use them to determine
   whether the overall intent is to support these features; otherwise,
   presume an older compiler has intent to support these features and
   define these macros by default.  */
/* wchar_t uses ISO/IEC 10646 (2nd ed., published 2011-03-15) /
   Unicode 6.0.  */
/* We do not support C11 <threads.h>.  */

int test_bitAnd_ARM(int x, int y)
{
}

int test_getByte_ARM(int x, int n)
{
}

int test_logicalShift_ARM(int x, int n) {
}


int test_bitCount_ARM(int x) {
}

int test_fitsBits_ARM(int x, int n)
{
}

int test_negate_ARM(int x) {
}

int test_isLessOrEqual(int x, int y)
{
}


int main(int argc, char *argv[]){

//Test Cases for bitAnd_ARM

assert(bitAnd_ARM(7,8)==test_bitAnd_ARM(7,8)); 
assert(bitAnd_ARM(1,3)==test_bitAnd_ARM(1,3));
assert(bitAnd_ARM(9,8)==test_bitAnd_ARM(9,8));
assert(bitAnd_ARM(0,6)==test_bitAnd_ARM(0,6));
assert(bitAnd_ARM(255,254)==test_bitAnd_ARM(255,254));
printf("Passed Test Cases for bitAnd_ARM\n");
/* To do: Run other test cases here */

return 0;

}
