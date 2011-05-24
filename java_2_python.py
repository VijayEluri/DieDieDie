import os
import sys

# Python script to convert a bunch of java source files into python
# source files. Requires java2python (j2py) installed from
# http://code.google.com/p/java2python

def java2python(files=[]):
    if files == []:
        print 'No files to convert.'
        return
    
    for f in files:
        outName = stripJavaExt(f) + '.py'
        print 'Converting "%s"...' % f 
        os.system('j2py -i %s -o %s' % (f, outName))
        print f, '-->', outName
    
def stripJavaExt(fileName):
    '''
    Returns a copy of fileName without the ".java"
    '''
    if not isJavaFile(fileName):
        exit('Non-java file in input :', f)
    return fileName[:-5]

def isJavaFile(fileName):
    return fileName.endswith('.java')

if __name__ == '__main__':
    if len(sys.argv) == 1:
        exit('No input files.')
    java2python(sys.argv[1:])
    
            
