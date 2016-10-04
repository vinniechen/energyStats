#
# Makefile that builds btest and other helper programs for the CSE30 Data Representation lab
# 
CC = gcc
CFLAGS = -O -Wall
LIBS = -lm

all: bits fshow ishow

bits: bits.c  bits.h
	$(CC) $(CFLAGS) $(LIBS) -o bits bits.c

fshow: fshow.c
	$(CC) $(CFLAGS) -o fshow fshow.c

ishow: ishow.c
	$(CC) $(CFLAGS) -o ishow ishow.c

# Forces a recompile. Used by the driver program.
btestexplicit:
	$(CC) $(CFLAGS) $(LIBS) -o bits bits.c

clean:
	rm -f *.o bits fshow ishow *~


