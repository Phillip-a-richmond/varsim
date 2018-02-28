#!/usr/bin/env python
import os
import subprocess
import sys

def run_shell_command(cmd, cmd_stdout, cmd_stderr, cmd_dir="."):
    subproc = subprocess.Popen(cmd, stdout=cmd_stdout, stderr=cmd_stderr, cwd=cmd_dir, shell=True, preexec_fn=os.setsid)
    retcode = subproc.wait()
    sys.exit(retcode)


def makedirs(dirs):
    for d in dirs:
        if not os.path.exists(d):
            os.makedirs(d)

def versatile_open(filename, mode):
    '''
    open regular file, gzipped files
    :param filename: filename string
    :param mode: mode string
    :return: file handle
    '''
    if filename.endswith('.gz'):
        import gzip
        return gzip.open(filename, mode)
    else:
        return open(filename, mode)
