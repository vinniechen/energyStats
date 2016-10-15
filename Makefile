#
# Makefile that builds btest and other helper programs for the CSE30 Data Representation lab
# 
CC = gcc
CFLAGS = -O -Wall -g
LIBS = -lm

all: bits bits_ARM fshow ishow

bits_ARM: bits_ARM.s bits_ARM.h test_bits_ARM.c 

bits: bits.c  bits.h test_bits.c
	$(CC) $(CFLAGS) $(LIBS) -o bits bits.c test_bits.c

fshow: fshow.c
	$(CC) $(CFLAGS) -o fshow fshow.c

ishow: ishow.c
	$(CC) $(CFLAGS) -o ishow ishow.c

# Forces a recompile. Used by the driver program.
btestexplicit:
	$(CC) $(CFLAGS) $(LIBS) -o bits bits.c

clean:
	rm -f *.o bits bits_ARM fshow ishow *~


