#include "silk.h"
#include "lame.h"
#include <stdio.h>

static void print_usage(char* argv[]) {
    printf( "\nusage: %s in.bit out.pcm [settings]\n", argv[ 0 ] );
    printf( "\nin.bit       : Bitstream input to decoder" );
    printf( "\nout.pcm      : Speech output from decoder" );
    printf( "\n   settings:" );
    printf( "\n-Fs_API <Hz> : Sampling rate of output signal in Hz; default: 24000" );
    printf( "\n-loss <perc> : Simulated packet loss percentage (0-100); default: 0" );
    printf( "\n-quiet       : Print out just some basic values" );
    printf( "\n" );
}

int main( int argc, char* argv[] )
{
//    print_usage(argv);

    const char *src = "/home/ketian/silk_project/silk/msg_3910190915165fe46499e0f103.amr";
    const char *dest = "/home/ketian/silk_project/silk/out2.pcm";
    return x(src, dest);
}
