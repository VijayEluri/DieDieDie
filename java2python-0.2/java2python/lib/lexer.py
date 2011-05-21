### $ANTLR 2.7.7 (20070206): "java.g" -> "lexer.py"$
### import antlr and other modules ..
import sys
import antlr

version = sys.version.split()[0]
if version < '2.2.1':
    False = 0
if version < '2.3':
    True = not False
### header action >>> 

### header action <<< 
### preamble action >>> 

### preamble action <<< 
### >>>The Literals<<<
literals = {}
literals[u"byte"] = 51
literals[u"public"] = 62
literals[u"case"] = 94
literals[u"short"] = 53
literals[u"break"] = 89
literals[u"while"] = 87
literals[u"new"] = 137
literals[u"instanceof"] = 122
literals[u"implements"] = 76
literals[u"synchronized"] = 68
literals[u"float"] = 55
literals[u"package"] = 44
literals[u"return"] = 91
literals[u"throw"] = 93
literals[u"null"] = 136
literals[u"threadsafe"] = 67
literals[u"protected"] = 63
literals[u"class"] = 70
literals[u"throws"] = 82
literals[u"do"] = 88
literals[u"strictfp"] = 41
literals[u"super"] = 80
literals[u"transient"] = 65
literals[u"native"] = 66
literals[u"interface"] = 72
literals[u"final"] = 39
literals[u"if"] = 84
literals[u"double"] = 57
literals[u"volatile"] = 69
literals[u"catch"] = 98
literals[u"try"] = 96
literals[u"int"] = 54
literals[u"for"] = 86
literals[u"extends"] = 71
literals[u"boolean"] = 50
literals[u"char"] = 52
literals[u"private"] = 61
literals[u"default"] = 95
literals[u"false"] = 135
literals[u"this"] = 79
literals[u"static"] = 64
literals[u"abstract"] = 40
literals[u"continue"] = 90
literals[u"finally"] = 97
literals[u"else"] = 85
literals[u"import"] = 46
literals[u"void"] = 49
literals[u"switch"] = 92
literals[u"true"] = 134
literals[u"long"] = 56


### import antlr.Token 
from antlr import Token
### >>>The Known Token Types <<<
SKIP                = antlr.SKIP
INVALID_TYPE        = antlr.INVALID_TYPE
EOF_TYPE            = antlr.EOF_TYPE
EOF                 = antlr.EOF
NULL_TREE_LOOKAHEAD = antlr.NULL_TREE_LOOKAHEAD
MIN_USER_TYPE       = antlr.MIN_USER_TYPE
BLOCK = 4
MODIFIERS = 5
OBJBLOCK = 6
SLIST = 7
CTOR_DEF = 8
METHOD_DEF = 9
VARIABLE_DEF = 10
INSTANCE_INIT = 11
STATIC_INIT = 12
TYPE = 13
CLASS_DEF = 14
INTERFACE_DEF = 15
PACKAGE_DEF = 16
ARRAY_DECLARATOR = 17
EXTENDS_CLAUSE = 18
IMPLEMENTS_CLAUSE = 19
PARAMETERS = 20
PARAMETER_DEF = 21
LABELED_STAT = 22
TYPECAST = 23
INDEX_OP = 24
POST_INC = 25
POST_DEC = 26
METHOD_CALL = 27
EXPR = 28
ARRAY_INIT = 29
IMPORT = 30
UNARY_MINUS = 31
UNARY_PLUS = 32
CASE_GROUP = 33
ELIST = 34
FOR_INIT = 35
FOR_CONDITION = 36
FOR_ITERATOR = 37
EMPTY_STAT = 38
FINAL = 39
ABSTRACT = 40
STRICTFP = 41
SUPER_CTOR_CALL = 42
CTOR_CALL = 43
LITERAL_package = 44
SEMI = 45
LITERAL_import = 46
LBRACK = 47
RBRACK = 48
LITERAL_void = 49
LITERAL_boolean = 50
LITERAL_byte = 51
LITERAL_char = 52
LITERAL_short = 53
LITERAL_int = 54
LITERAL_float = 55
LITERAL_long = 56
LITERAL_double = 57
IDENT = 58
DOT = 59
STAR = 60
LITERAL_private = 61
LITERAL_public = 62
LITERAL_protected = 63
LITERAL_static = 64
LITERAL_transient = 65
LITERAL_native = 66
LITERAL_threadsafe = 67
LITERAL_synchronized = 68
LITERAL_volatile = 69
LITERAL_class = 70
LITERAL_extends = 71
LITERAL_interface = 72
LCURLY = 73
RCURLY = 74
COMMA = 75
LITERAL_implements = 76
LPAREN = 77
RPAREN = 78
LITERAL_this = 79
LITERAL_super = 80
ASSIGN = 81
LITERAL_throws = 82
COLON = 83
LITERAL_if = 84
LITERAL_else = 85
LITERAL_for = 86
LITERAL_while = 87
LITERAL_do = 88
LITERAL_break = 89
LITERAL_continue = 90
LITERAL_return = 91
LITERAL_switch = 92
LITERAL_throw = 93
LITERAL_case = 94
LITERAL_default = 95
LITERAL_try = 96
LITERAL_finally = 97
LITERAL_catch = 98
PLUS_ASSIGN = 99
MINUS_ASSIGN = 100
STAR_ASSIGN = 101
DIV_ASSIGN = 102
MOD_ASSIGN = 103
SR_ASSIGN = 104
BSR_ASSIGN = 105
SL_ASSIGN = 106
BAND_ASSIGN = 107
BXOR_ASSIGN = 108
BOR_ASSIGN = 109
QUESTION = 110
LOR = 111
LAND = 112
BOR = 113
BXOR = 114
BAND = 115
NOT_EQUAL = 116
EQUAL = 117
LT = 118
GT = 119
LE = 120
GE = 121
LITERAL_instanceof = 122
SL = 123
SR = 124
BSR = 125
PLUS = 126
MINUS = 127
DIV = 128
MOD = 129
INC = 130
DEC = 131
BNOT = 132
LNOT = 133
LITERAL_true = 134
LITERAL_false = 135
LITERAL_null = 136
LITERAL_new = 137
NUM_INT = 138
CHAR_LITERAL = 139
STRING_LITERAL = 140
NUM_FLOAT = 141
NUM_LONG = 142
NUM_DOUBLE = 143
WS = 144
SL_COMMENT = 145
ML_COMMENT = 146
ESC = 147
HEX_DIGIT = 148
EXPONENT = 149
FLOAT_SUFFIX = 150

class Lexer(antlr.CharScanner) :
    ### user action >>>
    ### user action <<<
    def __init__(self, *argv, **kwargs) :
        antlr.CharScanner.__init__(self, *argv, **kwargs)
        self.caseSensitiveLiterals = True
        self.setCaseSensitive(True)
        self.literals = literals
    
    def nextToken(self):
        while True:
            try: ### try again ..
                while True:
                    _token = None
                    _ttype = INVALID_TYPE
                    self.resetText()
                    try: ## for char stream error handling
                        try: ##for lexical error handling
                            la1 = self.LA(1)
                            if False:
                                pass
                            elif la1 and la1 in u'?':
                                pass
                                self.mQUESTION(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'(':
                                pass
                                self.mLPAREN(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u')':
                                pass
                                self.mRPAREN(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'[':
                                pass
                                self.mLBRACK(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u']':
                                pass
                                self.mRBRACK(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'{':
                                pass
                                self.mLCURLY(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'}':
                                pass
                                self.mRCURLY(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u':':
                                pass
                                self.mCOLON(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u',':
                                pass
                                self.mCOMMA(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'~':
                                pass
                                self.mBNOT(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u';':
                                pass
                                self.mSEMI(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'\t\n\u000c\r ':
                                pass
                                self.mWS(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'\'':
                                pass
                                self.mCHAR_LITERAL(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'"':
                                pass
                                self.mSTRING_LITERAL(True)
                                theRetToken = self._returnToken
                            elif la1 and la1 in u'$ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz':
                                pass
                                self.mIDENT(True)
                                theRetToken = self._returnToken
                            else:
                                if (self.LA(1)==u'>') and (self.LA(2)==u'>') and (self.LA(3)==u'>') and (self.LA(4)==u'='):
                                    pass
                                    self.mBSR_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'>') and (self.LA(2)==u'>') and (self.LA(3)==u'='):
                                    pass
                                    self.mSR_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'>') and (self.LA(2)==u'>') and (self.LA(3)==u'>') and (True):
                                    pass
                                    self.mBSR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'<') and (self.LA(2)==u'<') and (self.LA(3)==u'='):
                                    pass
                                    self.mSL_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'=') and (self.LA(2)==u'='):
                                    pass
                                    self.mEQUAL(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'!') and (self.LA(2)==u'='):
                                    pass
                                    self.mNOT_EQUAL(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'/') and (self.LA(2)==u'='):
                                    pass
                                    self.mDIV_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'+') and (self.LA(2)==u'='):
                                    pass
                                    self.mPLUS_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'+') and (self.LA(2)==u'+'):
                                    pass
                                    self.mINC(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'-') and (self.LA(2)==u'='):
                                    pass
                                    self.mMINUS_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'-') and (self.LA(2)==u'-'):
                                    pass
                                    self.mDEC(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'*') and (self.LA(2)==u'='):
                                    pass
                                    self.mSTAR_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'%') and (self.LA(2)==u'='):
                                    pass
                                    self.mMOD_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'>') and (self.LA(2)==u'>') and (True):
                                    pass
                                    self.mSR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'>') and (self.LA(2)==u'='):
                                    pass
                                    self.mGE(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'<') and (self.LA(2)==u'<') and (True):
                                    pass
                                    self.mSL(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'<') and (self.LA(2)==u'='):
                                    pass
                                    self.mLE(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'^') and (self.LA(2)==u'='):
                                    pass
                                    self.mBXOR_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'|') and (self.LA(2)==u'='):
                                    pass
                                    self.mBOR_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'|') and (self.LA(2)==u'|'):
                                    pass
                                    self.mLOR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'&') and (self.LA(2)==u'='):
                                    pass
                                    self.mBAND_ASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'&') and (self.LA(2)==u'&'):
                                    pass
                                    self.mLAND(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'/') and (self.LA(2)==u'/'):
                                    pass
                                    self.mSL_COMMENT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'/') and (self.LA(2)==u'*'):
                                    pass
                                    self.mML_COMMENT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'.') and (True) and (True) and (True):
                                    pass
                                    self.mDOT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'=') and (True):
                                    pass
                                    self.mASSIGN(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'!') and (True):
                                    pass
                                    self.mLNOT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'/') and (True):
                                    pass
                                    self.mDIV(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'+') and (True):
                                    pass
                                    self.mPLUS(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'-') and (True):
                                    pass
                                    self.mMINUS(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'*') and (True):
                                    pass
                                    self.mSTAR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'%') and (True):
                                    pass
                                    self.mMOD(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'>') and (True):
                                    pass
                                    self.mGT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'<') and (True):
                                    pass
                                    self.mLT(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'^') and (True):
                                    pass
                                    self.mBXOR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'|') and (True):
                                    pass
                                    self.mBOR(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'&') and (True):
                                    pass
                                    self.mBAND(True)
                                    theRetToken = self._returnToken
                                elif (self.LA(1)==u'.' or self.LA(1)==u'0' or self.LA(1)==u'1' or self.LA(1)==u'2' or self.LA(1)==u'3' or self.LA(1)==u'4' or self.LA(1)==u'5' or self.LA(1)==u'6' or self.LA(1)==u'7' or self.LA(1)==u'8' or self.LA(1)==u'9') and (True) and (True) and (True):
                                    pass
                                    self.mNUM_INT(True)
                                    theRetToken = self._returnToken
                                else:
                                    self.default(self.LA(1))
                                
                            if not self._returnToken:
                                raise antlr.TryAgain ### found SKIP token
                            ### return token to caller
                            return self._returnToken
                        ### handle lexical errors ....
                        except antlr.RecognitionException, e:
                            raise antlr.TokenStreamRecognitionException(e)
                    ### handle char stream errors ...
                    except antlr.CharStreamException,cse:
                        if isinstance(cse, antlr.CharStreamIOException):
                            raise antlr.TokenStreamIOException(cse.io)
                        else:
                            raise antlr.TokenStreamException(str(cse))
            except antlr.TryAgain:
                pass
        
    def mQUESTION(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = QUESTION
        _saveIndex = 0
        pass
        self.match('?')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLPAREN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LPAREN
        _saveIndex = 0
        pass
        self.match('(')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mRPAREN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = RPAREN
        _saveIndex = 0
        pass
        self.match(')')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLBRACK(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LBRACK
        _saveIndex = 0
        pass
        self.match('[')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mRBRACK(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = RBRACK
        _saveIndex = 0
        pass
        self.match(']')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLCURLY(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LCURLY
        _saveIndex = 0
        pass
        self.match('{')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mRCURLY(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = RCURLY
        _saveIndex = 0
        pass
        self.match('}')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mCOLON(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = COLON
        _saveIndex = 0
        pass
        self.match(':')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mCOMMA(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = COMMA
        _saveIndex = 0
        pass
        self.match(',')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mDOT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = DOT
        _saveIndex = 0
        pass
        self.match('.')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = ASSIGN
        _saveIndex = 0
        pass
        self.match('=')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mEQUAL(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = EQUAL
        _saveIndex = 0
        pass
        self.match("==")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLNOT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LNOT
        _saveIndex = 0
        pass
        self.match('!')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBNOT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BNOT
        _saveIndex = 0
        pass
        self.match('~')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mNOT_EQUAL(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = NOT_EQUAL
        _saveIndex = 0
        pass
        self.match("!=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mDIV(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = DIV
        _saveIndex = 0
        pass
        self.match('/')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mDIV_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = DIV_ASSIGN
        _saveIndex = 0
        pass
        self.match("/=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mPLUS(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = PLUS
        _saveIndex = 0
        pass
        self.match('+')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mPLUS_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = PLUS_ASSIGN
        _saveIndex = 0
        pass
        self.match("+=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mINC(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = INC
        _saveIndex = 0
        pass
        self.match("++")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mMINUS(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = MINUS
        _saveIndex = 0
        pass
        self.match('-')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mMINUS_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = MINUS_ASSIGN
        _saveIndex = 0
        pass
        self.match("-=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mDEC(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = DEC
        _saveIndex = 0
        pass
        self.match("--")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSTAR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = STAR
        _saveIndex = 0
        pass
        self.match('*')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSTAR_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = STAR_ASSIGN
        _saveIndex = 0
        pass
        self.match("*=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mMOD(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = MOD
        _saveIndex = 0
        pass
        self.match('%')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mMOD_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = MOD_ASSIGN
        _saveIndex = 0
        pass
        self.match("%=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SR
        _saveIndex = 0
        pass
        self.match(">>")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSR_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SR_ASSIGN
        _saveIndex = 0
        pass
        self.match(">>=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBSR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BSR
        _saveIndex = 0
        pass
        self.match(">>>")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBSR_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BSR_ASSIGN
        _saveIndex = 0
        pass
        self.match(">>>=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mGE(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = GE
        _saveIndex = 0
        pass
        self.match(">=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mGT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = GT
        _saveIndex = 0
        pass
        self.match(">")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSL(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SL
        _saveIndex = 0
        pass
        self.match("<<")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSL_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SL_ASSIGN
        _saveIndex = 0
        pass
        self.match("<<=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLE(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LE
        _saveIndex = 0
        pass
        self.match("<=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LT
        _saveIndex = 0
        pass
        self.match('<')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBXOR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BXOR
        _saveIndex = 0
        pass
        self.match('^')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBXOR_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BXOR_ASSIGN
        _saveIndex = 0
        pass
        self.match("^=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBOR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BOR
        _saveIndex = 0
        pass
        self.match('|')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBOR_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BOR_ASSIGN
        _saveIndex = 0
        pass
        self.match("|=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLOR(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LOR
        _saveIndex = 0
        pass
        self.match("||")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBAND(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BAND
        _saveIndex = 0
        pass
        self.match('&')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mBAND_ASSIGN(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = BAND_ASSIGN
        _saveIndex = 0
        pass
        self.match("&=")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mLAND(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = LAND
        _saveIndex = 0
        pass
        self.match("&&")
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSEMI(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SEMI
        _saveIndex = 0
        pass
        self.match(';')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mWS(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = WS
        _saveIndex = 0
        pass
        _cnt247= 0
        while True:
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in u' ':
                pass
                self.match(' ')
            elif la1 and la1 in u'\t':
                pass
                self.match('\t')
            elif la1 and la1 in u'\u000c':
                pass
                self.match('\f')
            elif la1 and la1 in u'\n\r':
                pass
                if (self.LA(1)==u'\r') and (self.LA(2)==u'\n') and (True) and (True):
                    pass
                    self.match("\r\n")
                elif (self.LA(1)==u'\r') and (True) and (True) and (True):
                    pass
                    self.match('\r')
                elif (self.LA(1)==u'\n'):
                    pass
                    self.match('\n')
                else:
                    self.raise_NoViableAlt(self.LA(1))
                
                if not self.inputState.guessing:
                    self.newline()
            else:
                    break
                
            _cnt247 += 1
        if _cnt247 < 1:
            self.raise_NoViableAlt(self.LA(1))
        if not self.inputState.guessing:
            _ttype = SKIP
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSL_COMMENT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = SL_COMMENT
        _saveIndex = 0
        pass
        self.match("//")
        while True:
            if (_tokenSet_0.member(self.LA(1))):
                pass
                self.match(_tokenSet_0)
            else:
                break
            
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'\n':
            pass
            self.match('\n')
        elif la1 and la1 in u'\r':
            pass
            self.match('\r')
            if (self.LA(1)==u'\n'):
                pass
                self.match('\n')
            else: ## <m4>
                    pass
                
        else:
            ##<m3> <closing
                pass
            
        if not self.inputState.guessing:
            _ttype = SKIP ; self.newline()
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mML_COMMENT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = ML_COMMENT
        _saveIndex = 0
        pass
        self.match("/*")
        while True:
            if (self.LA(1)==u'\r') and (self.LA(2)==u'\n') and ((self.LA(3) >= u'\u0003' and self.LA(3) <= u'\u7ffe')) and ((self.LA(4) >= u'\u0003' and self.LA(4) <= u'\u7ffe')):
                pass
                self.match('\r')
                self.match('\n')
                if not self.inputState.guessing:
                    self.newline();
            elif ((self.LA(1)==u'*') and ((self.LA(2) >= u'\u0003' and self.LA(2) <= u'\u7ffe')) and ((self.LA(3) >= u'\u0003' and self.LA(3) <= u'\u7ffe')) and ( self.LA(2)!='/' )):
                pass
                self.match('*')
            elif (self.LA(1)==u'\r') and ((self.LA(2) >= u'\u0003' and self.LA(2) <= u'\u7ffe')) and ((self.LA(3) >= u'\u0003' and self.LA(3) <= u'\u7ffe')) and (True):
                pass
                self.match('\r')
                if not self.inputState.guessing:
                    self.newline();
            elif (self.LA(1)==u'\n'):
                pass
                self.match('\n')
                if not self.inputState.guessing:
                    self.newline();
            elif (_tokenSet_1.member(self.LA(1))):
                pass
                self.match(_tokenSet_1)
            else:
                break
            
        self.match("*/")
        if not self.inputState.guessing:
            _ttype = SKIP
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mCHAR_LITERAL(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = CHAR_LITERAL
        _saveIndex = 0
        pass
        self.match('\'')
        if (self.LA(1)==u'\\'):
            pass
            self.mESC(False)
        elif (_tokenSet_2.member(self.LA(1))):
            pass
            self.match(_tokenSet_2)
        else:
            self.raise_NoViableAlt(self.LA(1))
        
        self.match('\'')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mESC(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = ESC
        _saveIndex = 0
        pass
        self.match('\\')
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'n':
            pass
            self.match('n')
        elif la1 and la1 in u'r':
            pass
            self.match('r')
        elif la1 and la1 in u't':
            pass
            self.match('t')
        elif la1 and la1 in u'b':
            pass
            self.match('b')
        elif la1 and la1 in u'f':
            pass
            self.match('f')
        elif la1 and la1 in u'"':
            pass
            self.match('"')
        elif la1 and la1 in u'\'':
            pass
            self.match('\'')
        elif la1 and la1 in u'\\':
            pass
            self.match('\\')
        elif la1 and la1 in u'u':
            pass
            _cnt268= 0
            while True:
                if (self.LA(1)==u'u'):
                    pass
                    self.match('u')
                else:
                    break
                
                _cnt268 += 1
            if _cnt268 < 1:
                self.raise_NoViableAlt(self.LA(1))
            self.mHEX_DIGIT(False)
            self.mHEX_DIGIT(False)
            self.mHEX_DIGIT(False)
            self.mHEX_DIGIT(False)
        elif la1 and la1 in u'0123':
            pass
            self.matchRange(u'0', u'3')
            if ((self.LA(1) >= u'0' and self.LA(1) <= u'7')) and (_tokenSet_0.member(self.LA(2))) and (True) and (True):
                pass
                self.matchRange(u'0', u'7')
                if ((self.LA(1) >= u'0' and self.LA(1) <= u'7')) and (_tokenSet_0.member(self.LA(2))) and (True) and (True):
                    pass
                    self.matchRange(u'0', u'7')
                elif (_tokenSet_0.member(self.LA(1))) and (True) and (True) and (True):
                    pass
                else:
                    self.raise_NoViableAlt(self.LA(1))
                
            elif (_tokenSet_0.member(self.LA(1))) and (True) and (True) and (True):
                pass
            else:
                self.raise_NoViableAlt(self.LA(1))
            
        elif la1 and la1 in u'4567':
            pass
            self.matchRange(u'4', u'7')
            if ((self.LA(1) >= u'0' and self.LA(1) <= u'7')) and (_tokenSet_0.member(self.LA(2))) and (True) and (True):
                pass
                self.matchRange(u'0', u'7')
            elif (_tokenSet_0.member(self.LA(1))) and (True) and (True) and (True):
                pass
            else:
                self.raise_NoViableAlt(self.LA(1))
            
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mSTRING_LITERAL(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = STRING_LITERAL
        _saveIndex = 0
        pass
        self.match('"')
        while True:
            if (self.LA(1)==u'\\'):
                pass
                self.mESC(False)
            elif (_tokenSet_3.member(self.LA(1))):
                pass
                self.match(_tokenSet_3)
            else:
                break
            
        self.match('"')
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mHEX_DIGIT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = HEX_DIGIT
        _saveIndex = 0
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'0123456789':
            pass
            self.matchRange(u'0', u'9')
        elif la1 and la1 in u'ABCDEF':
            pass
            self.matchRange(u'A', u'F')
        elif la1 and la1 in u'abcdef':
            pass
            self.matchRange(u'a', u'f')
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mIDENT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = IDENT
        _saveIndex = 0
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'abcdefghijklmnopqrstuvwxyz':
            pass
            self.matchRange(u'a', u'z')
        elif la1 and la1 in u'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
            pass
            self.matchRange(u'A', u'Z')
        elif la1 and la1 in u'_':
            pass
            self.match('_')
        elif la1 and la1 in u'$':
            pass
            self.match('$')
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        while True:
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in u'abcdefghijklmnopqrstuvwxyz':
                pass
                self.matchRange(u'a', u'z')
            elif la1 and la1 in u'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
                pass
                self.matchRange(u'A', u'Z')
            elif la1 and la1 in u'_':
                pass
                self.match('_')
            elif la1 and la1 in u'0123456789':
                pass
                self.matchRange(u'0', u'9')
            elif la1 and la1 in u'$':
                pass
                self.match('$')
            else:
                    break
                
        ### option { testLiterals=true } 
        _ttype = self.testLiteralsTable(_ttype)
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mNUM_INT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = NUM_INT
        _saveIndex = 0
        f1 = None
        f2 = None
        f3 = None
        f4 = None
        isDecimal = False
        t = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'.':
            pass
            self.match('.')
            if not self.inputState.guessing:
                _ttype = DOT;
            if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                pass
                _cnt281= 0
                while True:
                    if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                        pass
                        self.matchRange(u'0', u'9')
                    else:
                        break
                    
                    _cnt281 += 1
                if _cnt281 < 1:
                    self.raise_NoViableAlt(self.LA(1))
                if (self.LA(1)==u'E' or self.LA(1)==u'e'):
                    pass
                    self.mEXPONENT(False)
                else: ## <m4>
                        pass
                    
                if (self.LA(1)==u'D' or self.LA(1)==u'F' or self.LA(1)==u'd' or self.LA(1)==u'f'):
                    pass
                    self.mFLOAT_SUFFIX(True)
                    f1 = self._returnToken
                    if not self.inputState.guessing:
                        t=f1
                else: ## <m4>
                        pass
                    
                if not self.inputState.guessing:
                    if t != None:
                       if 'F' in t.getText().upper():
                           _ttype = NUM_FLOAT
                       else:
                           _ttype = NUM_DOUBLE
            else: ## <m4>
                    pass
                
        elif la1 and la1 in u'0123456789':
            pass
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in u'0':
                pass
                self.match('0')
                if not self.inputState.guessing:
                    isDecimal = True;
                if (self.LA(1)==u'X' or self.LA(1)==u'x'):
                    pass
                    la1 = self.LA(1)
                    if False:
                        pass
                    elif la1 and la1 in u'x':
                        pass
                        self.match('x')
                    elif la1 and la1 in u'X':
                        pass
                        self.match('X')
                    else:
                            self.raise_NoViableAlt(self.LA(1))
                        
                    _cnt288= 0
                    while True:
                        if (_tokenSet_4.member(self.LA(1))) and (True) and (True) and (True):
                            pass
                            self.mHEX_DIGIT(False)
                        else:
                            break
                        
                        _cnt288 += 1
                    if _cnt288 < 1:
                        self.raise_NoViableAlt(self.LA(1))
                else:
                    synPredMatched293 = False
                    if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')) and (True) and (True) and (True):
                        _m293 = self.mark()
                        synPredMatched293 = True
                        self.inputState.guessing += 1
                        try:
                            pass
                            _cnt291= 0
                            while True:
                                if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                                    pass
                                    self.matchRange(u'0', u'9')
                                else:
                                    break
                                
                                _cnt291 += 1
                            if _cnt291 < 1:
                                self.raise_NoViableAlt(self.LA(1))
                            la1 = self.LA(1)
                            if False:
                                pass
                            elif la1 and la1 in u'.':
                                pass
                                self.match('.')
                            elif la1 and la1 in u'Ee':
                                pass
                                self.mEXPONENT(False)
                            elif la1 and la1 in u'DFdf':
                                pass
                                self.mFLOAT_SUFFIX(False)
                            else:
                                    self.raise_NoViableAlt(self.LA(1))
                                
                        except antlr.RecognitionException, pe:
                            synPredMatched293 = False
                        self.rewind(_m293)
                        self.inputState.guessing -= 1
                    if synPredMatched293:
                        pass
                        _cnt295= 0
                        while True:
                            if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                                pass
                                self.matchRange(u'0', u'9')
                            else:
                                break
                            
                            _cnt295 += 1
                        if _cnt295 < 1:
                            self.raise_NoViableAlt(self.LA(1))
                    elif ((self.LA(1) >= u'0' and self.LA(1) <= u'7')) and (True) and (True) and (True):
                        pass
                        _cnt297= 0
                        while True:
                            if ((self.LA(1) >= u'0' and self.LA(1) <= u'7')):
                                pass
                                self.matchRange(u'0', u'7')
                            else:
                                break
                            
                            _cnt297 += 1
                        if _cnt297 < 1:
                            self.raise_NoViableAlt(self.LA(1))
                    else: ## <m4>
                            pass
                        
            elif la1 and la1 in u'123456789':
                pass
                pass
                self.matchRange(u'1', u'9')
                while True:
                    if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                        pass
                        self.matchRange(u'0', u'9')
                    else:
                        break
                    
                if not self.inputState.guessing:
                    isDecimal=True;
            else:
                    self.raise_NoViableAlt(self.LA(1))
                
            if (self.LA(1)==u'L' or self.LA(1)==u'l'):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in u'l':
                    pass
                    self.match('l')
                elif la1 and la1 in u'L':
                    pass
                    self.match('L')
                else:
                        self.raise_NoViableAlt(self.LA(1))
                    
                if not self.inputState.guessing:
                    _ttype = NUM_LONG;
            elif ((self.LA(1)==u'.' or self.LA(1)==u'D' or self.LA(1)==u'E' or self.LA(1)==u'F' or self.LA(1)==u'd' or self.LA(1)==u'e' or self.LA(1)==u'f') and (isDecimal)):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in u'.':
                    pass
                    self.match('.')
                    while True:
                        if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                            pass
                            self.matchRange(u'0', u'9')
                        else:
                            break
                        
                    if (self.LA(1)==u'E' or self.LA(1)==u'e'):
                        pass
                        self.mEXPONENT(False)
                    else: ## <m4>
                            pass
                        
                    if (self.LA(1)==u'D' or self.LA(1)==u'F' or self.LA(1)==u'd' or self.LA(1)==u'f'):
                        pass
                        self.mFLOAT_SUFFIX(True)
                        f2 = self._returnToken
                        if not self.inputState.guessing:
                            t=f2
                    else: ## <m4>
                            pass
                        
                elif la1 and la1 in u'Ee':
                    pass
                    self.mEXPONENT(False)
                    if (self.LA(1)==u'D' or self.LA(1)==u'F' or self.LA(1)==u'd' or self.LA(1)==u'f'):
                        pass
                        self.mFLOAT_SUFFIX(True)
                        f3 = self._returnToken
                        if not self.inputState.guessing:
                            t=f3
                    else: ## <m4>
                            pass
                        
                elif la1 and la1 in u'DFdf':
                    pass
                    self.mFLOAT_SUFFIX(True)
                    f4 = self._returnToken
                    if not self.inputState.guessing:
                        t=f4
                else:
                        self.raise_NoViableAlt(self.LA(1))
                    
                if not self.inputState.guessing:
                    if t != None:
                       if 'F' in t.getText().upper():
                           _ttype = NUM_FLOAT
                       else:
                           _ttype = NUM_DOUBLE
            else: ## <m4>
                    pass
                
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mEXPONENT(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = EXPONENT
        _saveIndex = 0
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'e':
            pass
            self.match('e')
        elif la1 and la1 in u'E':
            pass
            self.match('E')
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'+':
            pass
            self.match('+')
        elif la1 and la1 in u'-':
            pass
            self.match('-')
        elif la1 and la1 in u'0123456789':
            pass
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        _cnt313= 0
        while True:
            if ((self.LA(1) >= u'0' and self.LA(1) <= u'9')):
                pass
                self.matchRange(u'0', u'9')
            else:
                break
            
            _cnt313 += 1
        if _cnt313 < 1:
            self.raise_NoViableAlt(self.LA(1))
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    def mFLOAT_SUFFIX(self, _createToken):    
        _ttype = 0
        _token = None
        _begin = self.text.length()
        _ttype = FLOAT_SUFFIX
        _saveIndex = 0
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in u'f':
            pass
            self.match('f')
        elif la1 and la1 in u'F':
            pass
            self.match('F')
        elif la1 and la1 in u'd':
            pass
            self.match('d')
        elif la1 and la1 in u'D':
            pass
            self.match('D')
        else:
                self.raise_NoViableAlt(self.LA(1))
            
        self.set_return_token(_createToken, _token, _ttype, _begin)
    
    

### generate bit set
def mk_tokenSet_0(): 
    data = [0L] * 1024 ### init list
    data[0] =-9224L
    for x in xrange(1, 511):
        data[x] = -1L
    data[511] =9223372036854775807L
    return data
_tokenSet_0 = antlr.BitSet(mk_tokenSet_0())

### generate bit set
def mk_tokenSet_1(): 
    data = [0L] * 1024 ### init list
    data[0] =-4398046520328L
    for x in xrange(1, 511):
        data[x] = -1L
    data[511] =9223372036854775807L
    return data
_tokenSet_1 = antlr.BitSet(mk_tokenSet_1())

### generate bit set
def mk_tokenSet_2(): 
    data = [0L] * 1024 ### init list
    data[0] =-549755823112L
    data[1] =-268435457L
    for x in xrange(2, 511):
        data[x] = -1L
    data[511] =9223372036854775807L
    return data
_tokenSet_2 = antlr.BitSet(mk_tokenSet_2())

### generate bit set
def mk_tokenSet_3(): 
    data = [0L] * 1024 ### init list
    data[0] =-17179878408L
    data[1] =-268435457L
    for x in xrange(2, 511):
        data[x] = -1L
    data[511] =9223372036854775807L
    return data
_tokenSet_3 = antlr.BitSet(mk_tokenSet_3())

### generate bit set
def mk_tokenSet_4(): 
    data = [0L] * 513 ### init list
    data[0] =287948901175001088L
    data[1] =541165879422L
    return data
_tokenSet_4 = antlr.BitSet(mk_tokenSet_4())
    
### __main__ header action >>> 
if __name__ == '__main__' :
    import sys
    import antlr
    import lexer
    
    ### create lexer - shall read from stdin
    try:
        for token in lexer.Lexer():
            print token
            
    except antlr.TokenStreamException, e:
        print "error: exception caught while lexing: ", e
### __main__ header action <<< 
