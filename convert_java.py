import os
import sys

def java_to_python(src=[]):
    '''
    Converts a list of java source files into
    python source files using java2python 
    (j2py).
    
    @src: A list of java source files from the 
          command line.
    '''
    if src == []:
        print 'No files specified.'
        return 0
    try:
        os.mkdir('j2py_converted')
    except:
        OSError('Already converted?')
    
    py_files = []
    for s in src:
        s_py = s[:-4] + '.py'
        command = 'j2py -i %s -o %s' % (s, s_py)
        #print command
        os.system(command)
        py_files.append(s_py)
    
    for x, y in enumerate(src):
        print y, '->', py_files[x]
    
    
if __name__ == '__main__':
    java_src = sys.argv[1:]
    
    if not all(a.endswith(u'.java') for a in java_src):
        print 'Non-java files in arguments.'
        exit(-1)       
    else:
        java_to_python(src=java_src)
