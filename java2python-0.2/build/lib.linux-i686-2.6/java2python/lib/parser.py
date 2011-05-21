### $ANTLR 2.7.7 (20070206): "java.g" -> "parser.py"$
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
### preamble action>>>

### preamble action <<<

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

class Parser(antlr.LLkParser):
    ### user action >>>
    ### user action <<<
    
    def __init__(self, *args, **kwargs):
        antlr.LLkParser.__init__(self, *args, **kwargs)
        self.tokenNames = _tokenNames
        self.buildTokenTypeASTClassMap()
        self.astFactory = antlr.ASTFactory(self.getTokenTypeToASTClassMap())
        self.astFactory.setASTNodeClass()
        
    def compilationUnit(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        compilationUnit_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_package]:
            pass
            self.packageDefinition()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [EOF,FINAL,ABSTRACT,STRICTFP,SEMI,LITERAL_import,LITERAL_private,LITERAL_public,LITERAL_protected,LITERAL_static,LITERAL_transient,LITERAL_native,LITERAL_threadsafe,LITERAL_synchronized,LITERAL_volatile,LITERAL_class,LITERAL_interface]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        while True:
            if (self.LA(1)==LITERAL_import):
                pass
                self.importDefinition()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        while True:
            if (_tokenSet_0.member(self.LA(1))):
                pass
                self.typeDefinition()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        self.match(EOF_TYPE)
        compilationUnit_AST = currentAST.root
        self.returnAST = compilationUnit_AST
    
    def packageDefinition(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        packageDefinition_AST = None
        p = None
        p_AST = None
        try:      ## for error handling
            pass
            p = self.LT(1)
            p_AST = self.astFactory.create(p)
            self.makeASTRoot(currentAST, p_AST)
            self.match(LITERAL_package)
            if not self.inputState.guessing:
                p_AST.setType(PACKAGE_DEF);
            self.identifier()
            self.addASTChild(currentAST, self.returnAST)
            self.match(SEMI)
            packageDefinition_AST = currentAST.root
        
        except antlr.RecognitionException, ex:
            if not self.inputState.guessing:
                self.reportError(ex)
                self.consume()
                self.consumeUntil(_tokenSet_1)
            else:
                raise ex
        
        self.returnAST = packageDefinition_AST
    
    def importDefinition(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        importDefinition_AST = None
        i = None
        i_AST = None
        try:      ## for error handling
            pass
            i = self.LT(1)
            i_AST = self.astFactory.create(i)
            self.makeASTRoot(currentAST, i_AST)
            self.match(LITERAL_import)
            if not self.inputState.guessing:
                i_AST.setType(IMPORT);
            self.identifierStar()
            self.addASTChild(currentAST, self.returnAST)
            self.match(SEMI)
            importDefinition_AST = currentAST.root
        
        except antlr.RecognitionException, ex:
            if not self.inputState.guessing:
                self.reportError(ex)
                self.consume()
                self.consumeUntil(_tokenSet_1)
            else:
                raise ex
        
        self.returnAST = importDefinition_AST
    
    def typeDefinition(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        typeDefinition_AST = None
        m_AST = None
        try:      ## for error handling
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [FINAL,ABSTRACT,STRICTFP,LITERAL_private,LITERAL_public,LITERAL_protected,LITERAL_static,LITERAL_transient,LITERAL_native,LITERAL_threadsafe,LITERAL_synchronized,LITERAL_volatile,LITERAL_class,LITERAL_interface]:
                pass
                self.modifiers()
                m_AST = self.returnAST
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [LITERAL_class]:
                    pass
                    self.classDefinition(m_AST)
                    self.addASTChild(currentAST, self.returnAST)
                elif la1 and la1 in [LITERAL_interface]:
                    pass
                    self.interfaceDefinition(m_AST)
                    self.addASTChild(currentAST, self.returnAST)
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                typeDefinition_AST = currentAST.root
            elif la1 and la1 in [SEMI]:
                pass
                self.match(SEMI)
                typeDefinition_AST = currentAST.root
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        
        except antlr.RecognitionException, ex:
            if not self.inputState.guessing:
                self.reportError(ex)
                self.consume()
                self.consumeUntil(_tokenSet_2)
            else:
                raise ex
        
        self.returnAST = typeDefinition_AST
    
    def identifier(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        identifier_AST = None
        pass
        tmp5_AST = None
        tmp5_AST = self.astFactory.create(self.LT(1))
        self.addASTChild(currentAST, tmp5_AST)
        self.match(IDENT)
        while True:
            if (self.LA(1)==DOT):
                pass
                tmp6_AST = None
                tmp6_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp6_AST)
                self.match(DOT)
                tmp7_AST = None
                tmp7_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp7_AST)
                self.match(IDENT)
            else:
                break
            
        identifier_AST = currentAST.root
        self.returnAST = identifier_AST
    
    def identifierStar(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        identifierStar_AST = None
        pass
        tmp8_AST = None
        tmp8_AST = self.astFactory.create(self.LT(1))
        self.addASTChild(currentAST, tmp8_AST)
        self.match(IDENT)
        while True:
            if (self.LA(1)==DOT) and (self.LA(2)==IDENT):
                pass
                tmp9_AST = None
                tmp9_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp9_AST)
                self.match(DOT)
                tmp10_AST = None
                tmp10_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp10_AST)
                self.match(IDENT)
            else:
                break
            
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [DOT]:
            pass
            tmp11_AST = None
            tmp11_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp11_AST)
            self.match(DOT)
            tmp12_AST = None
            tmp12_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp12_AST)
            self.match(STAR)
        elif la1 and la1 in [SEMI]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        identifierStar_AST = currentAST.root
        self.returnAST = identifierStar_AST
    
    def modifiers(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        modifiers_AST = None
        pass
        while True:
            if (_tokenSet_3.member(self.LA(1))):
                pass
                self.modifier()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        if not self.inputState.guessing:
            modifiers_AST = currentAST.root
            modifiers_AST = antlr.make(self.astFactory.create(MODIFIERS,"MODIFIERS"), modifiers_AST);
            currentAST.root = modifiers_AST
            if (modifiers_AST != None) and (modifiers_AST.getFirstChild() != None):
                currentAST.child = modifiers_AST.getFirstChild()
            else:
                currentAST.child = modifiers_AST
            currentAST.advanceChildToEnd()
        modifiers_AST = currentAST.root
        self.returnAST = modifiers_AST
    
    def classDefinition(self,
        modifiers
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        classDefinition_AST = None
        sc_AST = None
        ic_AST = None
        cb_AST = None
        pass
        self.match(LITERAL_class)
        tmp14_AST = None
        tmp14_AST = self.astFactory.create(self.LT(1))
        self.match(IDENT)
        self.superClassClause()
        sc_AST = self.returnAST
        self.implementsClause()
        ic_AST = self.returnAST
        self.classBlock()
        cb_AST = self.returnAST
        if not self.inputState.guessing:
            classDefinition_AST = currentAST.root
            classDefinition_AST = antlr.make(self.astFactory.create(CLASS_DEF,"CLASS_DEF"), modifiers, tmp14_AST, sc_AST, ic_AST, cb_AST);
            currentAST.root = classDefinition_AST
            if (classDefinition_AST != None) and (classDefinition_AST.getFirstChild() != None):
                currentAST.child = classDefinition_AST.getFirstChild()
            else:
                currentAST.child = classDefinition_AST
            currentAST.advanceChildToEnd()
        self.returnAST = classDefinition_AST
    
    def interfaceDefinition(self,
        modifiers
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        interfaceDefinition_AST = None
        ie_AST = None
        cb_AST = None
        pass
        self.match(LITERAL_interface)
        tmp16_AST = None
        tmp16_AST = self.astFactory.create(self.LT(1))
        self.match(IDENT)
        self.interfaceExtends()
        ie_AST = self.returnAST
        self.classBlock()
        cb_AST = self.returnAST
        if not self.inputState.guessing:
            interfaceDefinition_AST = currentAST.root
            interfaceDefinition_AST = antlr.make(self.astFactory.create(INTERFACE_DEF,"INTERFACE_DEF"), modifiers, tmp16_AST, ie_AST, cb_AST);
            currentAST.root = interfaceDefinition_AST
            if (interfaceDefinition_AST != None) and (interfaceDefinition_AST.getFirstChild() != None):
                currentAST.child = interfaceDefinition_AST.getFirstChild()
            else:
                currentAST.child = interfaceDefinition_AST
            currentAST.advanceChildToEnd()
        self.returnAST = interfaceDefinition_AST
    
    def declaration(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        declaration_AST = None
        m_AST = None
        t_AST = None
        v_AST = None
        pass
        self.modifiers()
        m_AST = self.returnAST
        self.typeSpec(False)
        t_AST = self.returnAST
        self.variableDefinitions(m_AST,t_AST)
        v_AST = self.returnAST
        if not self.inputState.guessing:
            declaration_AST = currentAST.root
            declaration_AST = v_AST;
            currentAST.root = declaration_AST
            if (declaration_AST != None) and (declaration_AST.getFirstChild() != None):
                currentAST.child = declaration_AST.getFirstChild()
            else:
                currentAST.child = declaration_AST
            currentAST.advanceChildToEnd()
        self.returnAST = declaration_AST
    
    def typeSpec(self,
        addImagNode
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        typeSpec_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [IDENT]:
            pass
            self.classTypeSpec(addImagNode)
            self.addASTChild(currentAST, self.returnAST)
            typeSpec_AST = currentAST.root
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double]:
            pass
            self.builtInTypeSpec(addImagNode)
            self.addASTChild(currentAST, self.returnAST)
            typeSpec_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = typeSpec_AST
    
    def variableDefinitions(self,
        mods,t
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        variableDefinitions_AST = None
        pass
        self.variableDeclarator(antlr.dupTree(mods,self.getASTFactory()),antlr.dupTree(t,self.getASTFactory()))
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==COMMA):
                pass
                self.match(COMMA)
                self.variableDeclarator(antlr.dupTree(mods,self.getASTFactory()),
                               antlr.dupTree(t,self.getASTFactory()))
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        variableDefinitions_AST = currentAST.root
        self.returnAST = variableDefinitions_AST
    
    def classTypeSpec(self,
        addImagNode
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        classTypeSpec_AST = None
        lb = None
        lb_AST = None
        pass
        self.identifier()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==LBRACK):
                pass
                lb = self.LT(1)
                lb_AST = self.astFactory.create(lb)
                self.makeASTRoot(currentAST, lb_AST)
                self.match(LBRACK)
                if not self.inputState.guessing:
                    lb_AST.setType(ARRAY_DECLARATOR);
                self.match(RBRACK)
            else:
                break
            
        if not self.inputState.guessing:
            classTypeSpec_AST = currentAST.root
            if addImagNode :
               classTypeSpec_AST = antlr.make(self.astFactory.create(TYPE,"TYPE"), classTypeSpec_AST);
            currentAST.root = classTypeSpec_AST
            if (classTypeSpec_AST != None) and (classTypeSpec_AST.getFirstChild() != None):
                currentAST.child = classTypeSpec_AST.getFirstChild()
            else:
                currentAST.child = classTypeSpec_AST
            currentAST.advanceChildToEnd()
        classTypeSpec_AST = currentAST.root
        self.returnAST = classTypeSpec_AST
    
    def builtInTypeSpec(self,
        addImagNode
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        builtInTypeSpec_AST = None
        lb = None
        lb_AST = None
        pass
        self.builtInType()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==LBRACK):
                pass
                lb = self.LT(1)
                lb_AST = self.astFactory.create(lb)
                self.makeASTRoot(currentAST, lb_AST)
                self.match(LBRACK)
                if not self.inputState.guessing:
                    lb_AST.setType(ARRAY_DECLARATOR);
                self.match(RBRACK)
            else:
                break
            
        if not self.inputState.guessing:
            builtInTypeSpec_AST = currentAST.root
            if addImagNode :
               builtInTypeSpec_AST = antlr.make(self.astFactory.create(TYPE,"TYPE"), builtInTypeSpec_AST);
            currentAST.root = builtInTypeSpec_AST
            if (builtInTypeSpec_AST != None) and (builtInTypeSpec_AST.getFirstChild() != None):
                currentAST.child = builtInTypeSpec_AST.getFirstChild()
            else:
                currentAST.child = builtInTypeSpec_AST
            currentAST.advanceChildToEnd()
        builtInTypeSpec_AST = currentAST.root
        self.returnAST = builtInTypeSpec_AST
    
    def builtInType(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        builtInType_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void]:
            pass
            tmp20_AST = None
            tmp20_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp20_AST)
            self.match(LITERAL_void)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_boolean]:
            pass
            tmp21_AST = None
            tmp21_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp21_AST)
            self.match(LITERAL_boolean)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_byte]:
            pass
            tmp22_AST = None
            tmp22_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp22_AST)
            self.match(LITERAL_byte)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_char]:
            pass
            tmp23_AST = None
            tmp23_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp23_AST)
            self.match(LITERAL_char)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_short]:
            pass
            tmp24_AST = None
            tmp24_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp24_AST)
            self.match(LITERAL_short)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_int]:
            pass
            tmp25_AST = None
            tmp25_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp25_AST)
            self.match(LITERAL_int)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_float]:
            pass
            tmp26_AST = None
            tmp26_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp26_AST)
            self.match(LITERAL_float)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_long]:
            pass
            tmp27_AST = None
            tmp27_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp27_AST)
            self.match(LITERAL_long)
            builtInType_AST = currentAST.root
        elif la1 and la1 in [LITERAL_double]:
            pass
            tmp28_AST = None
            tmp28_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp28_AST)
            self.match(LITERAL_double)
            builtInType_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = builtInType_AST
    
    def type(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        type_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [IDENT]:
            pass
            self.identifier()
            self.addASTChild(currentAST, self.returnAST)
            type_AST = currentAST.root
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double]:
            pass
            self.builtInType()
            self.addASTChild(currentAST, self.returnAST)
            type_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = type_AST
    
    def modifier(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        modifier_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_private]:
            pass
            tmp29_AST = None
            tmp29_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp29_AST)
            self.match(LITERAL_private)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_public]:
            pass
            tmp30_AST = None
            tmp30_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp30_AST)
            self.match(LITERAL_public)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_protected]:
            pass
            tmp31_AST = None
            tmp31_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp31_AST)
            self.match(LITERAL_protected)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_static]:
            pass
            tmp32_AST = None
            tmp32_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp32_AST)
            self.match(LITERAL_static)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_transient]:
            pass
            tmp33_AST = None
            tmp33_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp33_AST)
            self.match(LITERAL_transient)
            modifier_AST = currentAST.root
        elif la1 and la1 in [FINAL]:
            pass
            tmp34_AST = None
            tmp34_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp34_AST)
            self.match(FINAL)
            modifier_AST = currentAST.root
        elif la1 and la1 in [ABSTRACT]:
            pass
            tmp35_AST = None
            tmp35_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp35_AST)
            self.match(ABSTRACT)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_native]:
            pass
            tmp36_AST = None
            tmp36_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp36_AST)
            self.match(LITERAL_native)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_threadsafe]:
            pass
            tmp37_AST = None
            tmp37_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp37_AST)
            self.match(LITERAL_threadsafe)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_synchronized]:
            pass
            tmp38_AST = None
            tmp38_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp38_AST)
            self.match(LITERAL_synchronized)
            modifier_AST = currentAST.root
        elif la1 and la1 in [LITERAL_volatile]:
            pass
            tmp39_AST = None
            tmp39_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp39_AST)
            self.match(LITERAL_volatile)
            modifier_AST = currentAST.root
        elif la1 and la1 in [STRICTFP]:
            pass
            tmp40_AST = None
            tmp40_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp40_AST)
            self.match(STRICTFP)
            modifier_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = modifier_AST
    
    def superClassClause(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        superClassClause_AST = None
        id_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_extends]:
            pass
            self.match(LITERAL_extends)
            self.identifier()
            id_AST = self.returnAST
        elif la1 and la1 in [LCURLY,LITERAL_implements]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            superClassClause_AST = currentAST.root
            superClassClause_AST = antlr.make(self.astFactory.create(EXTENDS_CLAUSE,"EXTENDS_CLAUSE"), id_AST);
            currentAST.root = superClassClause_AST
            if (superClassClause_AST != None) and (superClassClause_AST.getFirstChild() != None):
                currentAST.child = superClassClause_AST.getFirstChild()
            else:
                currentAST.child = superClassClause_AST
            currentAST.advanceChildToEnd()
        self.returnAST = superClassClause_AST
    
    def implementsClause(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        implementsClause_AST = None
        i = None
        i_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_implements]:
            pass
            i = self.LT(1)
            i_AST = self.astFactory.create(i)
            self.match(LITERAL_implements)
            self.identifier()
            self.addASTChild(currentAST, self.returnAST)
            while True:
                if (self.LA(1)==COMMA):
                    pass
                    self.match(COMMA)
                    self.identifier()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
        elif la1 and la1 in [LCURLY]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            implementsClause_AST = currentAST.root
            implementsClause_AST = antlr.make(self.astFactory.create(IMPLEMENTS_CLAUSE,"IMPLEMENTS_CLAUSE"), implementsClause_AST);
            currentAST.root = implementsClause_AST
            if (implementsClause_AST != None) and (implementsClause_AST.getFirstChild() != None):
                currentAST.child = implementsClause_AST.getFirstChild()
            else:
                currentAST.child = implementsClause_AST
            currentAST.advanceChildToEnd()
        implementsClause_AST = currentAST.root
        self.returnAST = implementsClause_AST
    
    def classBlock(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        classBlock_AST = None
        pass
        self.match(LCURLY)
        while True:
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [FINAL,ABSTRACT,STRICTFP,LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LITERAL_private,LITERAL_public,LITERAL_protected,LITERAL_static,LITERAL_transient,LITERAL_native,LITERAL_threadsafe,LITERAL_synchronized,LITERAL_volatile,LITERAL_class,LITERAL_interface,LCURLY]:
                pass
                self.field()
                self.addASTChild(currentAST, self.returnAST)
            elif la1 and la1 in [SEMI]:
                pass
                self.match(SEMI)
            else:
                    break
                
        self.match(RCURLY)
        if not self.inputState.guessing:
            classBlock_AST = currentAST.root
            classBlock_AST = antlr.make(self.astFactory.create(OBJBLOCK,"OBJBLOCK"), classBlock_AST);
            currentAST.root = classBlock_AST
            if (classBlock_AST != None) and (classBlock_AST.getFirstChild() != None):
                currentAST.child = classBlock_AST.getFirstChild()
            else:
                currentAST.child = classBlock_AST
            currentAST.advanceChildToEnd()
        classBlock_AST = currentAST.root
        self.returnAST = classBlock_AST
    
    def interfaceExtends(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        interfaceExtends_AST = None
        e = None
        e_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_extends]:
            pass
            e = self.LT(1)
            e_AST = self.astFactory.create(e)
            self.match(LITERAL_extends)
            self.identifier()
            self.addASTChild(currentAST, self.returnAST)
            while True:
                if (self.LA(1)==COMMA):
                    pass
                    self.match(COMMA)
                    self.identifier()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
        elif la1 and la1 in [LCURLY]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            interfaceExtends_AST = currentAST.root
            interfaceExtends_AST = antlr.make(self.astFactory.create(EXTENDS_CLAUSE,"EXTENDS_CLAUSE"), interfaceExtends_AST);
            currentAST.root = interfaceExtends_AST
            if (interfaceExtends_AST != None) and (interfaceExtends_AST.getFirstChild() != None):
                currentAST.child = interfaceExtends_AST.getFirstChild()
            else:
                currentAST.child = interfaceExtends_AST
            currentAST.advanceChildToEnd()
        interfaceExtends_AST = currentAST.root
        self.returnAST = interfaceExtends_AST
    
    def field(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        field_AST = None
        mods_AST = None
        h_AST = None
        s_AST = None
        cd_AST = None
        id_AST = None
        t_AST = None
        param_AST = None
        rt_AST = None
        tc_AST = None
        s2_AST = None
        v_AST = None
        s3_AST = None
        s4_AST = None
        if (_tokenSet_4.member(self.LA(1))) and (_tokenSet_5.member(self.LA(2))):
            pass
            self.modifiers()
            mods_AST = self.returnAST
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [LITERAL_class]:
                pass
                self.classDefinition(mods_AST)
                cd_AST = self.returnAST
                if not self.inputState.guessing:
                    field_AST = currentAST.root
                    field_AST = cd_AST;
                    currentAST.root = field_AST
                    if (field_AST != None) and (field_AST.getFirstChild() != None):
                        currentAST.child = field_AST.getFirstChild()
                    else:
                        currentAST.child = field_AST
                    currentAST.advanceChildToEnd()
            elif la1 and la1 in [LITERAL_interface]:
                pass
                self.interfaceDefinition(mods_AST)
                id_AST = self.returnAST
                if not self.inputState.guessing:
                    field_AST = currentAST.root
                    field_AST = id_AST;
                    currentAST.root = field_AST
                    if (field_AST != None) and (field_AST.getFirstChild() != None):
                        currentAST.child = field_AST.getFirstChild()
                    else:
                        currentAST.child = field_AST
                    currentAST.advanceChildToEnd()
            else:
                if (self.LA(1)==IDENT) and (self.LA(2)==LPAREN):
                    pass
                    self.ctorHead()
                    h_AST = self.returnAST
                    self.constructorBody()
                    s_AST = self.returnAST
                    if not self.inputState.guessing:
                        field_AST = currentAST.root
                        field_AST = antlr.make(self.astFactory.create(CTOR_DEF,"CTOR_DEF"), mods_AST, h_AST, s_AST);
                        currentAST.root = field_AST
                        if (field_AST != None) and (field_AST.getFirstChild() != None):
                            currentAST.child = field_AST.getFirstChild()
                        else:
                            currentAST.child = field_AST
                        currentAST.advanceChildToEnd()
                elif ((self.LA(1) >= LITERAL_void and self.LA(1) <= IDENT)) and (_tokenSet_6.member(self.LA(2))):
                    pass
                    self.typeSpec(False)
                    t_AST = self.returnAST
                    if (self.LA(1)==IDENT) and (self.LA(2)==LPAREN):
                        pass
                        tmp47_AST = None
                        tmp47_AST = self.astFactory.create(self.LT(1))
                        self.match(IDENT)
                        self.match(LPAREN)
                        self.parameterDeclarationList()
                        param_AST = self.returnAST
                        self.match(RPAREN)
                        self.declaratorBrackets(t_AST)
                        rt_AST = self.returnAST
                        la1 = self.LA(1)
                        if False:
                            pass
                        elif la1 and la1 in [LITERAL_throws]:
                            pass
                            self.throwsClause()
                            tc_AST = self.returnAST
                        elif la1 and la1 in [SEMI,LCURLY]:
                            pass
                        else:
                                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                            
                        la1 = self.LA(1)
                        if False:
                            pass
                        elif la1 and la1 in [LCURLY]:
                            pass
                            self.compoundStatement()
                            s2_AST = self.returnAST
                        elif la1 and la1 in [SEMI]:
                            pass
                            tmp50_AST = None
                            tmp50_AST = self.astFactory.create(self.LT(1))
                            self.match(SEMI)
                        else:
                                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                            
                        if not self.inputState.guessing:
                            field_AST = currentAST.root
                            field_AST = antlr.make(self.astFactory.create(METHOD_DEF,"METHOD_DEF"), mods_AST, antlr.make(self.astFactory.create(TYPE,"TYPE"), rt_AST), tmp47_AST, param_AST, tc_AST, s2_AST);
                            currentAST.root = field_AST
                            if (field_AST != None) and (field_AST.getFirstChild() != None):
                                currentAST.child = field_AST.getFirstChild()
                            else:
                                currentAST.child = field_AST
                            currentAST.advanceChildToEnd()
                    elif (self.LA(1)==IDENT) and (_tokenSet_7.member(self.LA(2))):
                        pass
                        self.variableDefinitions(mods_AST,t_AST)
                        v_AST = self.returnAST
                        tmp51_AST = None
                        tmp51_AST = self.astFactory.create(self.LT(1))
                        self.match(SEMI)
                        if not self.inputState.guessing:
                            field_AST = currentAST.root
                            field_AST = v_AST;
                            currentAST.root = field_AST
                            if (field_AST != None) and (field_AST.getFirstChild() != None):
                                currentAST.child = field_AST.getFirstChild()
                            else:
                                currentAST.child = field_AST
                            currentAST.advanceChildToEnd()
                    else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        elif (self.LA(1)==LITERAL_static) and (self.LA(2)==LCURLY):
            pass
            self.match(LITERAL_static)
            self.compoundStatement()
            s3_AST = self.returnAST
            if not self.inputState.guessing:
                field_AST = currentAST.root
                field_AST = antlr.make(self.astFactory.create(STATIC_INIT,"STATIC_INIT"), s3_AST);
                currentAST.root = field_AST
                if (field_AST != None) and (field_AST.getFirstChild() != None):
                    currentAST.child = field_AST.getFirstChild()
                else:
                    currentAST.child = field_AST
                currentAST.advanceChildToEnd()
        elif (self.LA(1)==LCURLY):
            pass
            self.compoundStatement()
            s4_AST = self.returnAST
            if not self.inputState.guessing:
                field_AST = currentAST.root
                field_AST = antlr.make(self.astFactory.create(INSTANCE_INIT,"INSTANCE_INIT"), s4_AST);
                currentAST.root = field_AST
                if (field_AST != None) and (field_AST.getFirstChild() != None):
                    currentAST.child = field_AST.getFirstChild()
                else:
                    currentAST.child = field_AST
                currentAST.advanceChildToEnd()
        else:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        
        self.returnAST = field_AST
    
    def ctorHead(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        ctorHead_AST = None
        pass
        tmp53_AST = None
        tmp53_AST = self.astFactory.create(self.LT(1))
        self.addASTChild(currentAST, tmp53_AST)
        self.match(IDENT)
        self.match(LPAREN)
        self.parameterDeclarationList()
        self.addASTChild(currentAST, self.returnAST)
        self.match(RPAREN)
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_throws]:
            pass
            self.throwsClause()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [LCURLY]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        ctorHead_AST = currentAST.root
        self.returnAST = ctorHead_AST
    
    def constructorBody(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        constructorBody_AST = None
        lc = None
        lc_AST = None
        pass
        lc = self.LT(1)
        lc_AST = self.astFactory.create(lc)
        self.makeASTRoot(currentAST, lc_AST)
        self.match(LCURLY)
        if not self.inputState.guessing:
            lc_AST.setType(SLIST);
        if (self.LA(1)==LITERAL_this or self.LA(1)==LITERAL_super) and (self.LA(2)==LPAREN):
            pass
            self.explicitConstructorInvocation()
            self.addASTChild(currentAST, self.returnAST)
        elif (_tokenSet_8.member(self.LA(1))) and (_tokenSet_9.member(self.LA(2))):
            pass
        else:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        
        while True:
            if (_tokenSet_10.member(self.LA(1))):
                pass
                self.statement()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        self.match(RCURLY)
        constructorBody_AST = currentAST.root
        self.returnAST = constructorBody_AST
    
    def parameterDeclarationList(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        parameterDeclarationList_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [FINAL,LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT]:
            pass
            self.parameterDeclaration()
            self.addASTChild(currentAST, self.returnAST)
            while True:
                if (self.LA(1)==COMMA):
                    pass
                    self.match(COMMA)
                    self.parameterDeclaration()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
        elif la1 and la1 in [RPAREN]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            parameterDeclarationList_AST = currentAST.root
            parameterDeclarationList_AST = antlr.make(self.astFactory.create(PARAMETERS,"PARAMETERS"), parameterDeclarationList_AST);
            currentAST.root = parameterDeclarationList_AST
            if (parameterDeclarationList_AST != None) and (parameterDeclarationList_AST.getFirstChild() != None):
                currentAST.child = parameterDeclarationList_AST.getFirstChild()
            else:
                currentAST.child = parameterDeclarationList_AST
            currentAST.advanceChildToEnd()
        parameterDeclarationList_AST = currentAST.root
        self.returnAST = parameterDeclarationList_AST
    
    def declaratorBrackets(self,
        typ
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        declaratorBrackets_AST = None
        lb = None
        lb_AST = None
        pass
        if not self.inputState.guessing:
            declaratorBrackets_AST = currentAST.root
            declaratorBrackets_AST=typ;
            currentAST.root = declaratorBrackets_AST
            if (declaratorBrackets_AST != None) and (declaratorBrackets_AST.getFirstChild() != None):
                currentAST.child = declaratorBrackets_AST.getFirstChild()
            else:
                currentAST.child = declaratorBrackets_AST
            currentAST.advanceChildToEnd()
        while True:
            if (self.LA(1)==LBRACK):
                pass
                lb = self.LT(1)
                lb_AST = self.astFactory.create(lb)
                self.makeASTRoot(currentAST, lb_AST)
                self.match(LBRACK)
                if not self.inputState.guessing:
                    lb_AST.setType(ARRAY_DECLARATOR);
                self.match(RBRACK)
            else:
                break
            
        declaratorBrackets_AST = currentAST.root
        self.returnAST = declaratorBrackets_AST
    
    def throwsClause(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        throwsClause_AST = None
        pass
        tmp59_AST = None
        tmp59_AST = self.astFactory.create(self.LT(1))
        self.makeASTRoot(currentAST, tmp59_AST)
        self.match(LITERAL_throws)
        self.identifier()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==COMMA):
                pass
                self.match(COMMA)
                self.identifier()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        throwsClause_AST = currentAST.root
        self.returnAST = throwsClause_AST
    
    def compoundStatement(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        compoundStatement_AST = None
        lc = None
        lc_AST = None
        pass
        lc = self.LT(1)
        lc_AST = self.astFactory.create(lc)
        self.makeASTRoot(currentAST, lc_AST)
        self.match(LCURLY)
        if not self.inputState.guessing:
            lc_AST.setType(SLIST);
        while True:
            if (_tokenSet_10.member(self.LA(1))):
                pass
                self.statement()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        self.match(RCURLY)
        compoundStatement_AST = currentAST.root
        self.returnAST = compoundStatement_AST
    
    def explicitConstructorInvocation(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        explicitConstructorInvocation_AST = None
        lp1 = None
        lp1_AST = None
        lp2 = None
        lp2_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_this]:
            pass
            self.match(LITERAL_this)
            lp1 = self.LT(1)
            lp1_AST = self.astFactory.create(lp1)
            self.makeASTRoot(currentAST, lp1_AST)
            self.match(LPAREN)
            self.argList()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.match(SEMI)
            if not self.inputState.guessing:
                lp1_AST.setType(CTOR_CALL);
            explicitConstructorInvocation_AST = currentAST.root
        elif la1 and la1 in [LITERAL_super]:
            pass
            self.match(LITERAL_super)
            lp2 = self.LT(1)
            lp2_AST = self.astFactory.create(lp2)
            self.makeASTRoot(currentAST, lp2_AST)
            self.match(LPAREN)
            self.argList()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.match(SEMI)
            if not self.inputState.guessing:
                lp2_AST.setType(SUPER_CTOR_CALL);
            explicitConstructorInvocation_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = explicitConstructorInvocation_AST
    
    def statement(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        statement_AST = None
        m_AST = None
        c = None
        c_AST = None
        s = None
        s_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LCURLY]:
            pass
            self.compoundStatement()
            self.addASTChild(currentAST, self.returnAST)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_if]:
            pass
            tmp68_AST = None
            tmp68_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp68_AST)
            self.match(LITERAL_if)
            self.match(LPAREN)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.statement()
            self.addASTChild(currentAST, self.returnAST)
            if (self.LA(1)==LITERAL_else) and (_tokenSet_10.member(self.LA(2))):
                pass
                self.match(LITERAL_else)
                self.statement()
                self.addASTChild(currentAST, self.returnAST)
            elif (_tokenSet_11.member(self.LA(1))) and (_tokenSet_12.member(self.LA(2))):
                pass
            else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_for]:
            pass
            tmp72_AST = None
            tmp72_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp72_AST)
            self.match(LITERAL_for)
            self.match(LPAREN)
            self.forInit()
            self.addASTChild(currentAST, self.returnAST)
            self.match(SEMI)
            self.forCond()
            self.addASTChild(currentAST, self.returnAST)
            self.match(SEMI)
            self.forIter()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.statement()
            self.addASTChild(currentAST, self.returnAST)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_while]:
            pass
            tmp77_AST = None
            tmp77_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp77_AST)
            self.match(LITERAL_while)
            self.match(LPAREN)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.statement()
            self.addASTChild(currentAST, self.returnAST)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_do]:
            pass
            tmp80_AST = None
            tmp80_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp80_AST)
            self.match(LITERAL_do)
            self.statement()
            self.addASTChild(currentAST, self.returnAST)
            self.match(LITERAL_while)
            self.match(LPAREN)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.match(SEMI)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_break]:
            pass
            tmp85_AST = None
            tmp85_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp85_AST)
            self.match(LITERAL_break)
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [IDENT]:
                pass
                tmp86_AST = None
                tmp86_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp86_AST)
                self.match(IDENT)
            elif la1 and la1 in [SEMI]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
            self.match(SEMI)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_continue]:
            pass
            tmp88_AST = None
            tmp88_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp88_AST)
            self.match(LITERAL_continue)
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [IDENT]:
                pass
                tmp89_AST = None
                tmp89_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp89_AST)
                self.match(IDENT)
            elif la1 and la1 in [SEMI]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
            self.match(SEMI)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_return]:
            pass
            tmp91_AST = None
            tmp91_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp91_AST)
            self.match(LITERAL_return)
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                pass
                self.expression()
                self.addASTChild(currentAST, self.returnAST)
            elif la1 and la1 in [SEMI]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
            self.match(SEMI)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_switch]:
            pass
            tmp93_AST = None
            tmp93_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp93_AST)
            self.match(LITERAL_switch)
            self.match(LPAREN)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            self.match(LCURLY)
            while True:
                if (self.LA(1)==LITERAL_case or self.LA(1)==LITERAL_default):
                    pass
                    self.casesGroup()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
            self.match(RCURLY)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_try]:
            pass
            self.tryBlock()
            self.addASTChild(currentAST, self.returnAST)
            statement_AST = currentAST.root
        elif la1 and la1 in [LITERAL_throw]:
            pass
            tmp98_AST = None
            tmp98_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp98_AST)
            self.match(LITERAL_throw)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(SEMI)
            statement_AST = currentAST.root
        elif la1 and la1 in [SEMI]:
            pass
            s = self.LT(1)
            s_AST = self.astFactory.create(s)
            self.addASTChild(currentAST, s_AST)
            self.match(SEMI)
            if not self.inputState.guessing:
                s_AST.setType(EMPTY_STAT);
            statement_AST = currentAST.root
        else:
            synPredMatched89 = False
            if (_tokenSet_13.member(self.LA(1))) and (_tokenSet_14.member(self.LA(2))):
                _m89 = self.mark()
                synPredMatched89 = True
                self.inputState.guessing += 1
                try:
                    pass
                    self.declaration()
                except antlr.RecognitionException, pe:
                    synPredMatched89 = False
                self.rewind(_m89)
                self.inputState.guessing -= 1
            if synPredMatched89:
                pass
                self.declaration()
                self.addASTChild(currentAST, self.returnAST)
                self.match(SEMI)
                statement_AST = currentAST.root
            elif (_tokenSet_15.member(self.LA(1))) and (_tokenSet_16.member(self.LA(2))):
                pass
                self.expression()
                self.addASTChild(currentAST, self.returnAST)
                self.match(SEMI)
                statement_AST = currentAST.root
            elif (_tokenSet_17.member(self.LA(1))) and (_tokenSet_18.member(self.LA(2))):
                pass
                self.modifiers()
                m_AST = self.returnAST
                self.classDefinition(m_AST)
                self.addASTChild(currentAST, self.returnAST)
                statement_AST = currentAST.root
            elif (self.LA(1)==IDENT) and (self.LA(2)==COLON):
                pass
                tmp102_AST = None
                tmp102_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp102_AST)
                self.match(IDENT)
                c = self.LT(1)
                c_AST = self.astFactory.create(c)
                self.makeASTRoot(currentAST, c_AST)
                self.match(COLON)
                if not self.inputState.guessing:
                    c_AST.setType(LABELED_STAT);
                self.statement()
                self.addASTChild(currentAST, self.returnAST)
                statement_AST = currentAST.root
            elif (self.LA(1)==LITERAL_synchronized) and (self.LA(2)==LPAREN):
                pass
                tmp103_AST = None
                tmp103_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp103_AST)
                self.match(LITERAL_synchronized)
                self.match(LPAREN)
                self.expression()
                self.addASTChild(currentAST, self.returnAST)
                self.match(RPAREN)
                self.compoundStatement()
                self.addASTChild(currentAST, self.returnAST)
                statement_AST = currentAST.root
            else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = statement_AST
    
    def argList(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        argList_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.expressionList()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [RPAREN]:
            pass
            if not self.inputState.guessing:
                argList_AST = currentAST.root
                argList_AST = self.astFactory.create(ELIST,"ELIST");
                currentAST.root = argList_AST
                if (argList_AST != None) and (argList_AST.getFirstChild() != None):
                    currentAST.child = argList_AST.getFirstChild()
                else:
                    currentAST.child = argList_AST
                currentAST.advanceChildToEnd()
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        argList_AST = currentAST.root
        self.returnAST = argList_AST
    
    def variableDeclarator(self,
        mods,t
    ):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        variableDeclarator_AST = None
        id = None
        id_AST = None
        d_AST = None
        v_AST = None
        pass
        id = self.LT(1)
        id_AST = self.astFactory.create(id)
        self.match(IDENT)
        self.declaratorBrackets(t)
        d_AST = self.returnAST
        self.varInitializer()
        v_AST = self.returnAST
        if not self.inputState.guessing:
            variableDeclarator_AST = currentAST.root
            variableDeclarator_AST = antlr.make(self.astFactory.create(VARIABLE_DEF,"VARIABLE_DEF"), mods, antlr.make(self.astFactory.create(TYPE,"TYPE"), d_AST), id_AST, v_AST);
            currentAST.root = variableDeclarator_AST
            if (variableDeclarator_AST != None) and (variableDeclarator_AST.getFirstChild() != None):
                currentAST.child = variableDeclarator_AST.getFirstChild()
            else:
                currentAST.child = variableDeclarator_AST
            currentAST.advanceChildToEnd()
        self.returnAST = variableDeclarator_AST
    
    def varInitializer(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        varInitializer_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [ASSIGN]:
            pass
            tmp106_AST = None
            tmp106_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp106_AST)
            self.match(ASSIGN)
            self.initializer()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [SEMI,COMMA]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        varInitializer_AST = currentAST.root
        self.returnAST = varInitializer_AST
    
    def initializer(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        initializer_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
            initializer_AST = currentAST.root
        elif la1 and la1 in [LCURLY]:
            pass
            self.arrayInitializer()
            self.addASTChild(currentAST, self.returnAST)
            initializer_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = initializer_AST
    
    def arrayInitializer(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        arrayInitializer_AST = None
        lc = None
        lc_AST = None
        pass
        lc = self.LT(1)
        lc_AST = self.astFactory.create(lc)
        self.makeASTRoot(currentAST, lc_AST)
        self.match(LCURLY)
        if not self.inputState.guessing:
            lc_AST.setType(ARRAY_INIT);
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LCURLY,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.initializer()
            self.addASTChild(currentAST, self.returnAST)
            while True:
                if (self.LA(1)==COMMA) and (_tokenSet_19.member(self.LA(2))):
                    pass
                    self.match(COMMA)
                    self.initializer()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [COMMA]:
                pass
                self.match(COMMA)
            elif la1 and la1 in [RCURLY]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        elif la1 and la1 in [RCURLY]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.match(RCURLY)
        arrayInitializer_AST = currentAST.root
        self.returnAST = arrayInitializer_AST
    
    def expression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        expression_AST = None
        pass
        self.assignmentExpression()
        self.addASTChild(currentAST, self.returnAST)
        if not self.inputState.guessing:
            expression_AST = currentAST.root
            expression_AST = antlr.make(self.astFactory.create(EXPR,"EXPR"), expression_AST);
            currentAST.root = expression_AST
            if (expression_AST != None) and (expression_AST.getFirstChild() != None):
                currentAST.child = expression_AST.getFirstChild()
            else:
                currentAST.child = expression_AST
            currentAST.advanceChildToEnd()
        expression_AST = currentAST.root
        self.returnAST = expression_AST
    
    def parameterDeclaration(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        parameterDeclaration_AST = None
        pm_AST = None
        t_AST = None
        id = None
        id_AST = None
        pd_AST = None
        pass
        self.parameterModifier()
        pm_AST = self.returnAST
        self.typeSpec(False)
        t_AST = self.returnAST
        id = self.LT(1)
        id_AST = self.astFactory.create(id)
        self.match(IDENT)
        self.declaratorBrackets(t_AST)
        pd_AST = self.returnAST
        if not self.inputState.guessing:
            parameterDeclaration_AST = currentAST.root
            parameterDeclaration_AST = antlr.make(self.astFactory.create(PARAMETER_DEF,"PARAMETER_DEF"), pm_AST, antlr.make(self.astFactory.create(TYPE,"TYPE"), pd_AST), id_AST);
            currentAST.root = parameterDeclaration_AST
            if (parameterDeclaration_AST != None) and (parameterDeclaration_AST.getFirstChild() != None):
                currentAST.child = parameterDeclaration_AST.getFirstChild()
            else:
                currentAST.child = parameterDeclaration_AST
            currentAST.advanceChildToEnd()
        self.returnAST = parameterDeclaration_AST
    
    def parameterModifier(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        parameterModifier_AST = None
        f = None
        f_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [FINAL]:
            pass
            f = self.LT(1)
            f_AST = self.astFactory.create(f)
            self.addASTChild(currentAST, f_AST)
            self.match(FINAL)
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            parameterModifier_AST = currentAST.root
            parameterModifier_AST = antlr.make(self.astFactory.create(MODIFIERS,"MODIFIERS"), f_AST);
            currentAST.root = parameterModifier_AST
            if (parameterModifier_AST != None) and (parameterModifier_AST.getFirstChild() != None):
                currentAST.child = parameterModifier_AST.getFirstChild()
            else:
                currentAST.child = parameterModifier_AST
            currentAST.advanceChildToEnd()
        parameterModifier_AST = currentAST.root
        self.returnAST = parameterModifier_AST
    
    def forInit(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        forInit_AST = None
        pass
        synPredMatched107 = False
        if (_tokenSet_13.member(self.LA(1))) and (_tokenSet_14.member(self.LA(2))):
            _m107 = self.mark()
            synPredMatched107 = True
            self.inputState.guessing += 1
            try:
                pass
                self.declaration()
            except antlr.RecognitionException, pe:
                synPredMatched107 = False
            self.rewind(_m107)
            self.inputState.guessing -= 1
        if synPredMatched107:
            pass
            self.declaration()
            self.addASTChild(currentAST, self.returnAST)
        elif (_tokenSet_15.member(self.LA(1))) and (_tokenSet_20.member(self.LA(2))):
            pass
            self.expressionList()
            self.addASTChild(currentAST, self.returnAST)
        elif (self.LA(1)==SEMI):
            pass
        else:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        
        if not self.inputState.guessing:
            forInit_AST = currentAST.root
            forInit_AST = antlr.make(self.astFactory.create(FOR_INIT,"FOR_INIT"), forInit_AST);
            currentAST.root = forInit_AST
            if (forInit_AST != None) and (forInit_AST.getFirstChild() != None):
                currentAST.child = forInit_AST.getFirstChild()
            else:
                currentAST.child = forInit_AST
            currentAST.advanceChildToEnd()
        forInit_AST = currentAST.root
        self.returnAST = forInit_AST
    
    def forCond(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        forCond_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [SEMI]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            forCond_AST = currentAST.root
            forCond_AST = antlr.make(self.astFactory.create(FOR_CONDITION,"FOR_CONDITION"), forCond_AST);
            currentAST.root = forCond_AST
            if (forCond_AST != None) and (forCond_AST.getFirstChild() != None):
                currentAST.child = forCond_AST.getFirstChild()
            else:
                currentAST.child = forCond_AST
            currentAST.advanceChildToEnd()
        forCond_AST = currentAST.root
        self.returnAST = forCond_AST
    
    def forIter(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        forIter_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.expressionList()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [RPAREN]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        if not self.inputState.guessing:
            forIter_AST = currentAST.root
            forIter_AST = antlr.make(self.astFactory.create(FOR_ITERATOR,"FOR_ITERATOR"), forIter_AST);
            currentAST.root = forIter_AST
            if (forIter_AST != None) and (forIter_AST.getFirstChild() != None):
                currentAST.child = forIter_AST.getFirstChild()
            else:
                currentAST.child = forIter_AST
            currentAST.advanceChildToEnd()
        forIter_AST = currentAST.root
        self.returnAST = forIter_AST
    
    def casesGroup(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        casesGroup_AST = None
        pass
        _cnt98= 0
        while True:
            if (self.LA(1)==LITERAL_case or self.LA(1)==LITERAL_default) and (_tokenSet_21.member(self.LA(2))):
                pass
                self.aCase()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
            _cnt98 += 1
        if _cnt98 < 1:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        self.caseSList()
        self.addASTChild(currentAST, self.returnAST)
        if not self.inputState.guessing:
            casesGroup_AST = currentAST.root
            casesGroup_AST = antlr.make(self.astFactory.create(CASE_GROUP,"CASE_GROUP"), casesGroup_AST);
            currentAST.root = casesGroup_AST
            if (casesGroup_AST != None) and (casesGroup_AST.getFirstChild() != None):
                currentAST.child = casesGroup_AST.getFirstChild()
            else:
                currentAST.child = casesGroup_AST
            currentAST.advanceChildToEnd()
        casesGroup_AST = currentAST.root
        self.returnAST = casesGroup_AST
    
    def tryBlock(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        tryBlock_AST = None
        pass
        tmp110_AST = None
        tmp110_AST = self.astFactory.create(self.LT(1))
        self.makeASTRoot(currentAST, tmp110_AST)
        self.match(LITERAL_try)
        self.compoundStatement()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==LITERAL_catch):
                pass
                self.handler()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_finally]:
            pass
            self.finallyClause()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [FINAL,ABSTRACT,STRICTFP,SEMI,LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LITERAL_private,LITERAL_public,LITERAL_protected,LITERAL_static,LITERAL_transient,LITERAL_native,LITERAL_threadsafe,LITERAL_synchronized,LITERAL_volatile,LITERAL_class,LCURLY,RCURLY,LPAREN,LITERAL_this,LITERAL_super,LITERAL_if,LITERAL_else,LITERAL_for,LITERAL_while,LITERAL_do,LITERAL_break,LITERAL_continue,LITERAL_return,LITERAL_switch,LITERAL_throw,LITERAL_case,LITERAL_default,LITERAL_try,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        tryBlock_AST = currentAST.root
        self.returnAST = tryBlock_AST
    
    def aCase(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        aCase_AST = None
        pass
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LITERAL_case]:
            pass
            tmp111_AST = None
            tmp111_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp111_AST)
            self.match(LITERAL_case)
            self.expression()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [LITERAL_default]:
            pass
            tmp112_AST = None
            tmp112_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp112_AST)
            self.match(LITERAL_default)
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.match(COLON)
        aCase_AST = currentAST.root
        self.returnAST = aCase_AST
    
    def caseSList(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        caseSList_AST = None
        pass
        while True:
            if (_tokenSet_10.member(self.LA(1))):
                pass
                self.statement()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        if not self.inputState.guessing:
            caseSList_AST = currentAST.root
            caseSList_AST = antlr.make(self.astFactory.create(SLIST,"SLIST"), caseSList_AST);
            currentAST.root = caseSList_AST
            if (caseSList_AST != None) and (caseSList_AST.getFirstChild() != None):
                currentAST.child = caseSList_AST.getFirstChild()
            else:
                currentAST.child = caseSList_AST
            currentAST.advanceChildToEnd()
        caseSList_AST = currentAST.root
        self.returnAST = caseSList_AST
    
    def expressionList(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        expressionList_AST = None
        pass
        self.expression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==COMMA):
                pass
                self.match(COMMA)
                self.expression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        if not self.inputState.guessing:
            expressionList_AST = currentAST.root
            expressionList_AST = antlr.make(self.astFactory.create(ELIST,"ELIST"), expressionList_AST);
            currentAST.root = expressionList_AST
            if (expressionList_AST != None) and (expressionList_AST.getFirstChild() != None):
                currentAST.child = expressionList_AST.getFirstChild()
            else:
                currentAST.child = expressionList_AST
            currentAST.advanceChildToEnd()
        expressionList_AST = currentAST.root
        self.returnAST = expressionList_AST
    
    def handler(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        handler_AST = None
        pass
        tmp115_AST = None
        tmp115_AST = self.astFactory.create(self.LT(1))
        self.makeASTRoot(currentAST, tmp115_AST)
        self.match(LITERAL_catch)
        self.match(LPAREN)
        self.parameterDeclaration()
        self.addASTChild(currentAST, self.returnAST)
        self.match(RPAREN)
        self.compoundStatement()
        self.addASTChild(currentAST, self.returnAST)
        handler_AST = currentAST.root
        self.returnAST = handler_AST
    
    def finallyClause(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        finallyClause_AST = None
        pass
        tmp118_AST = None
        tmp118_AST = self.astFactory.create(self.LT(1))
        self.makeASTRoot(currentAST, tmp118_AST)
        self.match(LITERAL_finally)
        self.compoundStatement()
        self.addASTChild(currentAST, self.returnAST)
        finallyClause_AST = currentAST.root
        self.returnAST = finallyClause_AST
    
    def assignmentExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        assignmentExpression_AST = None
        pass
        self.conditionalExpression()
        self.addASTChild(currentAST, self.returnAST)
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN]:
            pass
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [ASSIGN]:
                pass
                tmp119_AST = None
                tmp119_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp119_AST)
                self.match(ASSIGN)
            elif la1 and la1 in [PLUS_ASSIGN]:
                pass
                tmp120_AST = None
                tmp120_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp120_AST)
                self.match(PLUS_ASSIGN)
            elif la1 and la1 in [MINUS_ASSIGN]:
                pass
                tmp121_AST = None
                tmp121_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp121_AST)
                self.match(MINUS_ASSIGN)
            elif la1 and la1 in [STAR_ASSIGN]:
                pass
                tmp122_AST = None
                tmp122_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp122_AST)
                self.match(STAR_ASSIGN)
            elif la1 and la1 in [DIV_ASSIGN]:
                pass
                tmp123_AST = None
                tmp123_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp123_AST)
                self.match(DIV_ASSIGN)
            elif la1 and la1 in [MOD_ASSIGN]:
                pass
                tmp124_AST = None
                tmp124_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp124_AST)
                self.match(MOD_ASSIGN)
            elif la1 and la1 in [SR_ASSIGN]:
                pass
                tmp125_AST = None
                tmp125_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp125_AST)
                self.match(SR_ASSIGN)
            elif la1 and la1 in [BSR_ASSIGN]:
                pass
                tmp126_AST = None
                tmp126_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp126_AST)
                self.match(BSR_ASSIGN)
            elif la1 and la1 in [SL_ASSIGN]:
                pass
                tmp127_AST = None
                tmp127_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp127_AST)
                self.match(SL_ASSIGN)
            elif la1 and la1 in [BAND_ASSIGN]:
                pass
                tmp128_AST = None
                tmp128_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp128_AST)
                self.match(BAND_ASSIGN)
            elif la1 and la1 in [BXOR_ASSIGN]:
                pass
                tmp129_AST = None
                tmp129_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp129_AST)
                self.match(BXOR_ASSIGN)
            elif la1 and la1 in [BOR_ASSIGN]:
                pass
                tmp130_AST = None
                tmp130_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp130_AST)
                self.match(BOR_ASSIGN)
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
            self.assignmentExpression()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [SEMI,RBRACK,RCURLY,COMMA,RPAREN,COLON]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        assignmentExpression_AST = currentAST.root
        self.returnAST = assignmentExpression_AST
    
    def conditionalExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        conditionalExpression_AST = None
        pass
        self.logicalOrExpression()
        self.addASTChild(currentAST, self.returnAST)
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [QUESTION]:
            pass
            tmp131_AST = None
            tmp131_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp131_AST)
            self.match(QUESTION)
            self.assignmentExpression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(COLON)
            self.conditionalExpression()
            self.addASTChild(currentAST, self.returnAST)
        elif la1 and la1 in [SEMI,RBRACK,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        conditionalExpression_AST = currentAST.root
        self.returnAST = conditionalExpression_AST
    
    def logicalOrExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        logicalOrExpression_AST = None
        pass
        self.logicalAndExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==LOR):
                pass
                tmp133_AST = None
                tmp133_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp133_AST)
                self.match(LOR)
                self.logicalAndExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        logicalOrExpression_AST = currentAST.root
        self.returnAST = logicalOrExpression_AST
    
    def logicalAndExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        logicalAndExpression_AST = None
        pass
        self.inclusiveOrExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==LAND):
                pass
                tmp134_AST = None
                tmp134_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp134_AST)
                self.match(LAND)
                self.inclusiveOrExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        logicalAndExpression_AST = currentAST.root
        self.returnAST = logicalAndExpression_AST
    
    def inclusiveOrExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        inclusiveOrExpression_AST = None
        pass
        self.exclusiveOrExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==BOR):
                pass
                tmp135_AST = None
                tmp135_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp135_AST)
                self.match(BOR)
                self.exclusiveOrExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        inclusiveOrExpression_AST = currentAST.root
        self.returnAST = inclusiveOrExpression_AST
    
    def exclusiveOrExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        exclusiveOrExpression_AST = None
        pass
        self.andExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==BXOR):
                pass
                tmp136_AST = None
                tmp136_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp136_AST)
                self.match(BXOR)
                self.andExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        exclusiveOrExpression_AST = currentAST.root
        self.returnAST = exclusiveOrExpression_AST
    
    def andExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        andExpression_AST = None
        pass
        self.equalityExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==BAND):
                pass
                tmp137_AST = None
                tmp137_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp137_AST)
                self.match(BAND)
                self.equalityExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        andExpression_AST = currentAST.root
        self.returnAST = andExpression_AST
    
    def equalityExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        equalityExpression_AST = None
        pass
        self.relationalExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==NOT_EQUAL or self.LA(1)==EQUAL):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [NOT_EQUAL]:
                    pass
                    tmp138_AST = None
                    tmp138_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp138_AST)
                    self.match(NOT_EQUAL)
                elif la1 and la1 in [EQUAL]:
                    pass
                    tmp139_AST = None
                    tmp139_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp139_AST)
                    self.match(EQUAL)
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                self.relationalExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        equalityExpression_AST = currentAST.root
        self.returnAST = equalityExpression_AST
    
    def relationalExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        relationalExpression_AST = None
        pass
        self.shiftExpression()
        self.addASTChild(currentAST, self.returnAST)
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [SEMI,RBRACK,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE]:
            pass
            while True:
                if ((self.LA(1) >= LT and self.LA(1) <= GE)):
                    pass
                    la1 = self.LA(1)
                    if False:
                        pass
                    elif la1 and la1 in [LT]:
                        pass
                        tmp140_AST = None
                        tmp140_AST = self.astFactory.create(self.LT(1))
                        self.makeASTRoot(currentAST, tmp140_AST)
                        self.match(LT)
                    elif la1 and la1 in [GT]:
                        pass
                        tmp141_AST = None
                        tmp141_AST = self.astFactory.create(self.LT(1))
                        self.makeASTRoot(currentAST, tmp141_AST)
                        self.match(GT)
                    elif la1 and la1 in [LE]:
                        pass
                        tmp142_AST = None
                        tmp142_AST = self.astFactory.create(self.LT(1))
                        self.makeASTRoot(currentAST, tmp142_AST)
                        self.match(LE)
                    elif la1 and la1 in [GE]:
                        pass
                        tmp143_AST = None
                        tmp143_AST = self.astFactory.create(self.LT(1))
                        self.makeASTRoot(currentAST, tmp143_AST)
                        self.match(GE)
                    else:
                            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                        
                    self.shiftExpression()
                    self.addASTChild(currentAST, self.returnAST)
                else:
                    break
                
        elif la1 and la1 in [LITERAL_instanceof]:
            pass
            tmp144_AST = None
            tmp144_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp144_AST)
            self.match(LITERAL_instanceof)
            self.typeSpec(True)
            self.addASTChild(currentAST, self.returnAST)
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        relationalExpression_AST = currentAST.root
        self.returnAST = relationalExpression_AST
    
    def shiftExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        shiftExpression_AST = None
        pass
        self.additiveExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if ((self.LA(1) >= SL and self.LA(1) <= BSR)):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [SL]:
                    pass
                    tmp145_AST = None
                    tmp145_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp145_AST)
                    self.match(SL)
                elif la1 and la1 in [SR]:
                    pass
                    tmp146_AST = None
                    tmp146_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp146_AST)
                    self.match(SR)
                elif la1 and la1 in [BSR]:
                    pass
                    tmp147_AST = None
                    tmp147_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp147_AST)
                    self.match(BSR)
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                self.additiveExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        shiftExpression_AST = currentAST.root
        self.returnAST = shiftExpression_AST
    
    def additiveExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        additiveExpression_AST = None
        pass
        self.multiplicativeExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==PLUS or self.LA(1)==MINUS):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [PLUS]:
                    pass
                    tmp148_AST = None
                    tmp148_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp148_AST)
                    self.match(PLUS)
                elif la1 and la1 in [MINUS]:
                    pass
                    tmp149_AST = None
                    tmp149_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp149_AST)
                    self.match(MINUS)
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                self.multiplicativeExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        additiveExpression_AST = currentAST.root
        self.returnAST = additiveExpression_AST
    
    def multiplicativeExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        multiplicativeExpression_AST = None
        pass
        self.unaryExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (_tokenSet_22.member(self.LA(1))):
                pass
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [STAR]:
                    pass
                    tmp150_AST = None
                    tmp150_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp150_AST)
                    self.match(STAR)
                elif la1 and la1 in [DIV]:
                    pass
                    tmp151_AST = None
                    tmp151_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp151_AST)
                    self.match(DIV)
                elif la1 and la1 in [MOD]:
                    pass
                    tmp152_AST = None
                    tmp152_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp152_AST)
                    self.match(MOD)
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                self.unaryExpression()
                self.addASTChild(currentAST, self.returnAST)
            else:
                break
            
        multiplicativeExpression_AST = currentAST.root
        self.returnAST = multiplicativeExpression_AST
    
    def unaryExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        unaryExpression_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [INC]:
            pass
            tmp153_AST = None
            tmp153_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp153_AST)
            self.match(INC)
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpression_AST = currentAST.root
        elif la1 and la1 in [DEC]:
            pass
            tmp154_AST = None
            tmp154_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp154_AST)
            self.match(DEC)
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpression_AST = currentAST.root
        elif la1 and la1 in [MINUS]:
            pass
            tmp155_AST = None
            tmp155_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp155_AST)
            self.match(MINUS)
            if not self.inputState.guessing:
                tmp155_AST.setType(UNARY_MINUS);
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpression_AST = currentAST.root
        elif la1 and la1 in [PLUS]:
            pass
            tmp156_AST = None
            tmp156_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp156_AST)
            self.match(PLUS)
            if not self.inputState.guessing:
                tmp156_AST.setType(UNARY_PLUS);
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.unaryExpressionNotPlusMinus()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpression_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = unaryExpression_AST
    
    def unaryExpressionNotPlusMinus(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        unaryExpressionNotPlusMinus_AST = None
        lpb = None
        lpb_AST = None
        lp = None
        lp_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [BNOT]:
            pass
            tmp157_AST = None
            tmp157_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp157_AST)
            self.match(BNOT)
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpressionNotPlusMinus_AST = currentAST.root
        elif la1 and la1 in [LNOT]:
            pass
            tmp158_AST = None
            tmp158_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp158_AST)
            self.match(LNOT)
            self.unaryExpression()
            self.addASTChild(currentAST, self.returnAST)
            unaryExpressionNotPlusMinus_AST = currentAST.root
        else:
            synPredMatched166 = False
            if (self.LA(1)==LPAREN) and ((self.LA(2) >= LITERAL_void and self.LA(2) <= LITERAL_double)):
                _m166 = self.mark()
                synPredMatched166 = True
                self.inputState.guessing += 1
                try:
                    pass
                    self.match(LPAREN)
                    self.builtInTypeSpec(True)
                    self.match(RPAREN)
                except antlr.RecognitionException, pe:
                    synPredMatched166 = False
                self.rewind(_m166)
                self.inputState.guessing -= 1
            if synPredMatched166:
                pass
                lpb = self.LT(1)
                lpb_AST = self.astFactory.create(lpb)
                self.makeASTRoot(currentAST, lpb_AST)
                self.match(LPAREN)
                if not self.inputState.guessing:
                    lpb_AST.setType(TYPECAST);
                self.builtInTypeSpec(True)
                self.addASTChild(currentAST, self.returnAST)
                self.match(RPAREN)
                self.unaryExpression()
                self.addASTChild(currentAST, self.returnAST)
                unaryExpressionNotPlusMinus_AST = currentAST.root
            else:
                synPredMatched168 = False
                if (self.LA(1)==LPAREN) and (self.LA(2)==IDENT):
                    _m168 = self.mark()
                    synPredMatched168 = True
                    self.inputState.guessing += 1
                    try:
                        pass
                        self.match(LPAREN)
                        self.classTypeSpec(True)
                        self.match(RPAREN)
                        self.unaryExpressionNotPlusMinus()
                    except antlr.RecognitionException, pe:
                        synPredMatched168 = False
                    self.rewind(_m168)
                    self.inputState.guessing -= 1
                if synPredMatched168:
                    pass
                    lp = self.LT(1)
                    lp_AST = self.astFactory.create(lp)
                    self.makeASTRoot(currentAST, lp_AST)
                    self.match(LPAREN)
                    if not self.inputState.guessing:
                        lp_AST.setType(TYPECAST);
                    self.classTypeSpec(True)
                    self.addASTChild(currentAST, self.returnAST)
                    self.match(RPAREN)
                    self.unaryExpressionNotPlusMinus()
                    self.addASTChild(currentAST, self.returnAST)
                    unaryExpressionNotPlusMinus_AST = currentAST.root
                elif (_tokenSet_23.member(self.LA(1))) and (_tokenSet_24.member(self.LA(2))):
                    pass
                    self.postfixExpression()
                    self.addASTChild(currentAST, self.returnAST)
                    unaryExpressionNotPlusMinus_AST = currentAST.root
                else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        self.returnAST = unaryExpressionNotPlusMinus_AST
    
    def postfixExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        postfixExpression_AST = None
        lp = None
        lp_AST = None
        lp3 = None
        lp3_AST = None
        lps = None
        lps_AST = None
        lb = None
        lb_AST = None
        inc = None
        inc_AST = None
        dec = None
        dec_AST = None
        pass
        self.primaryExpression()
        self.addASTChild(currentAST, self.returnAST)
        while True:
            if (self.LA(1)==DOT) and (self.LA(2)==IDENT):
                pass
                tmp161_AST = None
                tmp161_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp161_AST)
                self.match(DOT)
                tmp162_AST = None
                tmp162_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp162_AST)
                self.match(IDENT)
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [LPAREN]:
                    pass
                    lp = self.LT(1)
                    lp_AST = self.astFactory.create(lp)
                    self.makeASTRoot(currentAST, lp_AST)
                    self.match(LPAREN)
                    if not self.inputState.guessing:
                        lp_AST.setType(METHOD_CALL);
                    self.argList()
                    self.addASTChild(currentAST, self.returnAST)
                    self.match(RPAREN)
                elif la1 and la1 in [SEMI,LBRACK,RBRACK,DOT,STAR,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,INC,DEC]:
                    pass
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
            elif (self.LA(1)==DOT) and (self.LA(2)==LITERAL_this):
                pass
                tmp164_AST = None
                tmp164_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp164_AST)
                self.match(DOT)
                tmp165_AST = None
                tmp165_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp165_AST)
                self.match(LITERAL_this)
            elif (self.LA(1)==DOT) and (self.LA(2)==LITERAL_super):
                pass
                tmp166_AST = None
                tmp166_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp166_AST)
                self.match(DOT)
                tmp167_AST = None
                tmp167_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp167_AST)
                self.match(LITERAL_super)
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [LPAREN]:
                    pass
                    lp3 = self.LT(1)
                    lp3_AST = self.astFactory.create(lp3)
                    self.makeASTRoot(currentAST, lp3_AST)
                    self.match(LPAREN)
                    self.argList()
                    self.addASTChild(currentAST, self.returnAST)
                    self.match(RPAREN)
                    if not self.inputState.guessing:
                        lp3_AST.setType(SUPER_CTOR_CALL);
                elif la1 and la1 in [DOT]:
                    pass
                    tmp169_AST = None
                    tmp169_AST = self.astFactory.create(self.LT(1))
                    self.makeASTRoot(currentAST, tmp169_AST)
                    self.match(DOT)
                    tmp170_AST = None
                    tmp170_AST = self.astFactory.create(self.LT(1))
                    self.addASTChild(currentAST, tmp170_AST)
                    self.match(IDENT)
                    la1 = self.LA(1)
                    if False:
                        pass
                    elif la1 and la1 in [LPAREN]:
                        pass
                        lps = self.LT(1)
                        lps_AST = self.astFactory.create(lps)
                        self.makeASTRoot(currentAST, lps_AST)
                        self.match(LPAREN)
                        if not self.inputState.guessing:
                            lps_AST.setType(METHOD_CALL);
                        self.argList()
                        self.addASTChild(currentAST, self.returnAST)
                        self.match(RPAREN)
                    elif la1 and la1 in [SEMI,LBRACK,RBRACK,DOT,STAR,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,INC,DEC]:
                        pass
                    else:
                            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                        
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
            elif (self.LA(1)==DOT) and (self.LA(2)==LITERAL_new):
                pass
                tmp172_AST = None
                tmp172_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp172_AST)
                self.match(DOT)
                self.newExpression()
                self.addASTChild(currentAST, self.returnAST)
            elif (self.LA(1)==LBRACK):
                pass
                lb = self.LT(1)
                lb_AST = self.astFactory.create(lb)
                self.makeASTRoot(currentAST, lb_AST)
                self.match(LBRACK)
                if not self.inputState.guessing:
                    lb_AST.setType(INDEX_OP);
                self.expression()
                self.addASTChild(currentAST, self.returnAST)
                self.match(RBRACK)
            else:
                break
            
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [INC]:
            pass
            inc = self.LT(1)
            inc_AST = self.astFactory.create(inc)
            self.makeASTRoot(currentAST, inc_AST)
            self.match(INC)
            if not self.inputState.guessing:
                inc_AST.setType(POST_INC);
        elif la1 and la1 in [DEC]:
            pass
            dec = self.LT(1)
            dec_AST = self.astFactory.create(dec)
            self.makeASTRoot(currentAST, dec_AST)
            self.match(DEC)
            if not self.inputState.guessing:
                dec_AST.setType(POST_DEC);
        elif la1 and la1 in [SEMI,RBRACK,STAR,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD]:
            pass
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        postfixExpression_AST = currentAST.root
        self.returnAST = postfixExpression_AST
    
    def primaryExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        primaryExpression_AST = None
        lbt = None
        lbt_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [IDENT]:
            pass
            self.identPrimary()
            self.addASTChild(currentAST, self.returnAST)
            if (self.LA(1)==DOT) and (self.LA(2)==LITERAL_class):
                pass
                tmp174_AST = None
                tmp174_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp174_AST)
                self.match(DOT)
                tmp175_AST = None
                tmp175_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp175_AST)
                self.match(LITERAL_class)
            elif (_tokenSet_25.member(self.LA(1))) and (_tokenSet_26.member(self.LA(2))):
                pass
            else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
            pass
            self.constant()
            self.addASTChild(currentAST, self.returnAST)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_true]:
            pass
            tmp176_AST = None
            tmp176_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp176_AST)
            self.match(LITERAL_true)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_false]:
            pass
            tmp177_AST = None
            tmp177_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp177_AST)
            self.match(LITERAL_false)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_null]:
            pass
            tmp178_AST = None
            tmp178_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp178_AST)
            self.match(LITERAL_null)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_new]:
            pass
            self.newExpression()
            self.addASTChild(currentAST, self.returnAST)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_this]:
            pass
            tmp179_AST = None
            tmp179_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp179_AST)
            self.match(LITERAL_this)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_super]:
            pass
            tmp180_AST = None
            tmp180_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp180_AST)
            self.match(LITERAL_super)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LPAREN]:
            pass
            self.match(LPAREN)
            self.assignmentExpression()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            primaryExpression_AST = currentAST.root
        elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double]:
            pass
            self.builtInType()
            self.addASTChild(currentAST, self.returnAST)
            while True:
                if (self.LA(1)==LBRACK):
                    pass
                    lbt = self.LT(1)
                    lbt_AST = self.astFactory.create(lbt)
                    self.makeASTRoot(currentAST, lbt_AST)
                    self.match(LBRACK)
                    if not self.inputState.guessing:
                        lbt_AST.setType(ARRAY_DECLARATOR);
                    self.match(RBRACK)
                else:
                    break
                
            tmp184_AST = None
            tmp184_AST = self.astFactory.create(self.LT(1))
            self.makeASTRoot(currentAST, tmp184_AST)
            self.match(DOT)
            tmp185_AST = None
            tmp185_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp185_AST)
            self.match(LITERAL_class)
            primaryExpression_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = primaryExpression_AST
    
    def newExpression(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        newExpression_AST = None
        pass
        tmp186_AST = None
        tmp186_AST = self.astFactory.create(self.LT(1))
        self.makeASTRoot(currentAST, tmp186_AST)
        self.match(LITERAL_new)
        self.type()
        self.addASTChild(currentAST, self.returnAST)
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [LPAREN]:
            pass
            self.match(LPAREN)
            self.argList()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [LCURLY]:
                pass
                self.classBlock()
                self.addASTChild(currentAST, self.returnAST)
            elif la1 and la1 in [SEMI,LBRACK,RBRACK,DOT,STAR,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,INC,DEC]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        elif la1 and la1 in [LBRACK]:
            pass
            self.newArrayDeclarator()
            self.addASTChild(currentAST, self.returnAST)
            la1 = self.LA(1)
            if False:
                pass
            elif la1 and la1 in [LCURLY]:
                pass
                self.arrayInitializer()
                self.addASTChild(currentAST, self.returnAST)
            elif la1 and la1 in [SEMI,LBRACK,RBRACK,DOT,STAR,RCURLY,COMMA,RPAREN,ASSIGN,COLON,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,INC,DEC]:
                pass
            else:
                    raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        newExpression_AST = currentAST.root
        self.returnAST = newExpression_AST
    
    def identPrimary(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        identPrimary_AST = None
        lp = None
        lp_AST = None
        lbc = None
        lbc_AST = None
        pass
        tmp189_AST = None
        tmp189_AST = self.astFactory.create(self.LT(1))
        self.addASTChild(currentAST, tmp189_AST)
        self.match(IDENT)
        while True:
            if (self.LA(1)==DOT) and (self.LA(2)==IDENT):
                pass
                tmp190_AST = None
                tmp190_AST = self.astFactory.create(self.LT(1))
                self.makeASTRoot(currentAST, tmp190_AST)
                self.match(DOT)
                tmp191_AST = None
                tmp191_AST = self.astFactory.create(self.LT(1))
                self.addASTChild(currentAST, tmp191_AST)
                self.match(IDENT)
            else:
                break
            
        if (self.LA(1)==LPAREN):
            pass
            pass
            lp = self.LT(1)
            lp_AST = self.astFactory.create(lp)
            self.makeASTRoot(currentAST, lp_AST)
            self.match(LPAREN)
            if not self.inputState.guessing:
                lp_AST.setType(METHOD_CALL);
            self.argList()
            self.addASTChild(currentAST, self.returnAST)
            self.match(RPAREN)
        elif (self.LA(1)==LBRACK) and (self.LA(2)==RBRACK):
            pass
            _cnt186= 0
            while True:
                if (self.LA(1)==LBRACK) and (self.LA(2)==RBRACK):
                    pass
                    lbc = self.LT(1)
                    lbc_AST = self.astFactory.create(lbc)
                    self.makeASTRoot(currentAST, lbc_AST)
                    self.match(LBRACK)
                    if not self.inputState.guessing:
                        lbc_AST.setType(ARRAY_DECLARATOR);
                    self.match(RBRACK)
                else:
                    break
                
                _cnt186 += 1
            if _cnt186 < 1:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        elif (_tokenSet_25.member(self.LA(1))) and (_tokenSet_26.member(self.LA(2))):
            pass
        else:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        
        identPrimary_AST = currentAST.root
        self.returnAST = identPrimary_AST
    
    def constant(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        constant_AST = None
        la1 = self.LA(1)
        if False:
            pass
        elif la1 and la1 in [NUM_INT]:
            pass
            tmp194_AST = None
            tmp194_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp194_AST)
            self.match(NUM_INT)
            constant_AST = currentAST.root
        elif la1 and la1 in [CHAR_LITERAL]:
            pass
            tmp195_AST = None
            tmp195_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp195_AST)
            self.match(CHAR_LITERAL)
            constant_AST = currentAST.root
        elif la1 and la1 in [STRING_LITERAL]:
            pass
            tmp196_AST = None
            tmp196_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp196_AST)
            self.match(STRING_LITERAL)
            constant_AST = currentAST.root
        elif la1 and la1 in [NUM_FLOAT]:
            pass
            tmp197_AST = None
            tmp197_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp197_AST)
            self.match(NUM_FLOAT)
            constant_AST = currentAST.root
        elif la1 and la1 in [NUM_LONG]:
            pass
            tmp198_AST = None
            tmp198_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp198_AST)
            self.match(NUM_LONG)
            constant_AST = currentAST.root
        elif la1 and la1 in [NUM_DOUBLE]:
            pass
            tmp199_AST = None
            tmp199_AST = self.astFactory.create(self.LT(1))
            self.addASTChild(currentAST, tmp199_AST)
            self.match(NUM_DOUBLE)
            constant_AST = currentAST.root
        else:
                raise antlr.NoViableAltException(self.LT(1), self.getFilename())
            
        self.returnAST = constant_AST
    
    def newArrayDeclarator(self):    
        
        self.returnAST = None
        currentAST = antlr.ASTPair()
        newArrayDeclarator_AST = None
        lb = None
        lb_AST = None
        pass
        _cnt196= 0
        while True:
            if (self.LA(1)==LBRACK) and (_tokenSet_27.member(self.LA(2))):
                pass
                lb = self.LT(1)
                lb_AST = self.astFactory.create(lb)
                self.makeASTRoot(currentAST, lb_AST)
                self.match(LBRACK)
                if not self.inputState.guessing:
                    lb_AST.setType(ARRAY_DECLARATOR);
                la1 = self.LA(1)
                if False:
                    pass
                elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,LPAREN,LITERAL_this,LITERAL_super,PLUS,MINUS,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                    pass
                    self.expression()
                    self.addASTChild(currentAST, self.returnAST)
                elif la1 and la1 in [RBRACK]:
                    pass
                else:
                        raise antlr.NoViableAltException(self.LT(1), self.getFilename())
                    
                self.match(RBRACK)
            else:
                break
            
            _cnt196 += 1
        if _cnt196 < 1:
            raise antlr.NoViableAltException(self.LT(1), self.getFilename())
        newArrayDeclarator_AST = currentAST.root
        self.returnAST = newArrayDeclarator_AST
    
    
    def buildTokenTypeASTClassMap(self):
        self.tokenTypeToASTClassMap = None

_tokenNames = [
    "<0>", 
    "EOF", 
    "<2>", 
    "NULL_TREE_LOOKAHEAD", 
    "BLOCK", 
    "MODIFIERS", 
    "OBJBLOCK", 
    "SLIST", 
    "CTOR_DEF", 
    "METHOD_DEF", 
    "VARIABLE_DEF", 
    "INSTANCE_INIT", 
    "STATIC_INIT", 
    "TYPE", 
    "CLASS_DEF", 
    "INTERFACE_DEF", 
    "PACKAGE_DEF", 
    "ARRAY_DECLARATOR", 
    "EXTENDS_CLAUSE", 
    "IMPLEMENTS_CLAUSE", 
    "PARAMETERS", 
    "PARAMETER_DEF", 
    "LABELED_STAT", 
    "TYPECAST", 
    "INDEX_OP", 
    "POST_INC", 
    "POST_DEC", 
    "METHOD_CALL", 
    "EXPR", 
    "ARRAY_INIT", 
    "IMPORT", 
    "UNARY_MINUS", 
    "UNARY_PLUS", 
    "CASE_GROUP", 
    "ELIST", 
    "FOR_INIT", 
    "FOR_CONDITION", 
    "FOR_ITERATOR", 
    "EMPTY_STAT", 
    "\"final\"", 
    "\"abstract\"", 
    "\"strictfp\"", 
    "SUPER_CTOR_CALL", 
    "CTOR_CALL", 
    "\"package\"", 
    "SEMI", 
    "\"import\"", 
    "LBRACK", 
    "RBRACK", 
    "\"void\"", 
    "\"boolean\"", 
    "\"byte\"", 
    "\"char\"", 
    "\"short\"", 
    "\"int\"", 
    "\"float\"", 
    "\"long\"", 
    "\"double\"", 
    "IDENT", 
    "DOT", 
    "STAR", 
    "\"private\"", 
    "\"public\"", 
    "\"protected\"", 
    "\"static\"", 
    "\"transient\"", 
    "\"native\"", 
    "\"threadsafe\"", 
    "\"synchronized\"", 
    "\"volatile\"", 
    "\"class\"", 
    "\"extends\"", 
    "\"interface\"", 
    "LCURLY", 
    "RCURLY", 
    "COMMA", 
    "\"implements\"", 
    "LPAREN", 
    "RPAREN", 
    "\"this\"", 
    "\"super\"", 
    "ASSIGN", 
    "\"throws\"", 
    "COLON", 
    "\"if\"", 
    "\"else\"", 
    "\"for\"", 
    "\"while\"", 
    "\"do\"", 
    "\"break\"", 
    "\"continue\"", 
    "\"return\"", 
    "\"switch\"", 
    "\"throw\"", 
    "\"case\"", 
    "\"default\"", 
    "\"try\"", 
    "\"finally\"", 
    "\"catch\"", 
    "PLUS_ASSIGN", 
    "MINUS_ASSIGN", 
    "STAR_ASSIGN", 
    "DIV_ASSIGN", 
    "MOD_ASSIGN", 
    "SR_ASSIGN", 
    "BSR_ASSIGN", 
    "SL_ASSIGN", 
    "BAND_ASSIGN", 
    "BXOR_ASSIGN", 
    "BOR_ASSIGN", 
    "QUESTION", 
    "LOR", 
    "LAND", 
    "BOR", 
    "BXOR", 
    "BAND", 
    "NOT_EQUAL", 
    "EQUAL", 
    "LT", 
    "GT", 
    "LE", 
    "GE", 
    "\"instanceof\"", 
    "SL", 
    "SR", 
    "BSR", 
    "PLUS", 
    "MINUS", 
    "DIV", 
    "MOD", 
    "INC", 
    "DEC", 
    "BNOT", 
    "LNOT", 
    "\"true\"", 
    "\"false\"", 
    "\"null\"", 
    "\"new\"", 
    "NUM_INT", 
    "CHAR_LITERAL", 
    "STRING_LITERAL", 
    "NUM_FLOAT", 
    "NUM_LONG", 
    "NUM_DOUBLE", 
    "WS", 
    "SL_COMMENT", 
    "ML_COMMENT", 
    "ESC", 
    "HEX_DIGIT", 
    "EXPONENT", 
    "FLOAT_SUFFIX"
]
    

### generate bit set
def mk_tokenSet_0(): 
    ### var1
    data = [ -2305803976550907904L, 383L, 0L, 0L]
    return data
_tokenSet_0 = antlr.BitSet(mk_tokenSet_0())

### generate bit set
def mk_tokenSet_1(): 
    ### var1
    data = [ -2305733607806730238L, 383L, 0L, 0L]
    return data
_tokenSet_1 = antlr.BitSet(mk_tokenSet_1())

### generate bit set
def mk_tokenSet_2(): 
    ### var1
    data = [ -2305803976550907902L, 383L, 0L, 0L]
    return data
_tokenSet_2 = antlr.BitSet(mk_tokenSet_2())

### generate bit set
def mk_tokenSet_3(): 
    ### var1
    data = [ -2305839160922996736L, 63L, 0L, 0L]
    return data
_tokenSet_3 = antlr.BitSet(mk_tokenSet_3())

### generate bit set
def mk_tokenSet_4(): 
    ### var1
    data = [ -1729941358572994560L, 383L, 0L, 0L]
    return data
_tokenSet_4 = antlr.BitSet(mk_tokenSet_4())

### generate bit set
def mk_tokenSet_5(): 
    ### var1
    data = [ -1153339868781215744L, 8575L, 0L, 0L]
    return data
_tokenSet_5 = antlr.BitSet(mk_tokenSet_5())

### generate bit set
def mk_tokenSet_6(): 
    ### var1
    data = [ 864831865943490560L, 0L, 0L]
    return data
_tokenSet_6 = antlr.BitSet(mk_tokenSet_6())

### generate bit set
def mk_tokenSet_7(): 
    ### var1
    data = [ 175921860444160L, 133120L, 0L, 0L]
    return data
_tokenSet_7 = antlr.BitSet(mk_tokenSet_7())

### generate bit set
def mk_tokenSet_8(): 
    ### var1
    data = [ -1729906174200905728L, -4611686013061716353L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_8 = antlr.BitSet(mk_tokenSet_8())

### generate bit set
def mk_tokenSet_9(): 
    ### var1
    data = [ -383179802279936L, -28993411201L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_9 = antlr.BitSet(mk_tokenSet_9())

### generate bit set
def mk_tokenSet_10(): 
    ### var1
    data = [ -1729906174200905728L, -4611686013061717377L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_10 = antlr.BitSet(mk_tokenSet_10())

### generate bit set
def mk_tokenSet_11(): 
    ### var1
    data = [ -1729906174200905728L, -4611686009838393729L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_11 = antlr.BitSet(mk_tokenSet_11())

### generate bit set
def mk_tokenSet_12(): 
    ### var1
    data = [ -383179802279936L, -284801L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_12 = antlr.BitSet(mk_tokenSet_12())

### generate bit set
def mk_tokenSet_13(): 
    ### var1
    data = [ -1729941358572994560L, 63L, 0L, 0L]
    return data
_tokenSet_13 = antlr.BitSet(mk_tokenSet_13())

### generate bit set
def mk_tokenSet_14(): 
    ### var1
    data = [ -1153339868781215744L, 63L, 0L, 0L]
    return data
_tokenSet_14 = antlr.BitSet(mk_tokenSet_14())

### generate bit set
def mk_tokenSet_15(): 
    ### var1
    data = [ 575897802350002176L, -4611686018427281408L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_15 = antlr.BitSet(mk_tokenSet_15())

### generate bit set
def mk_tokenSet_16(): 
    ### var1
    data = [ 2305455981120716800L, -34359500800L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_16 = antlr.BitSet(mk_tokenSet_16())

### generate bit set
def mk_tokenSet_17(): 
    ### var1
    data = [ -2305839160922996736L, 127L, 0L, 0L]
    return data
_tokenSet_17 = antlr.BitSet(mk_tokenSet_17())

### generate bit set
def mk_tokenSet_18(): 
    ### var1
    data = [ -2017608784771284992L, 127L, 0L, 0L]
    return data
_tokenSet_18 = antlr.BitSet(mk_tokenSet_18())

### generate bit set
def mk_tokenSet_19(): 
    ### var1
    data = [ 575897802350002176L, -4611686018427280896L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_19 = antlr.BitSet(mk_tokenSet_19())

### generate bit set
def mk_tokenSet_20(): 
    ### var1
    data = [ 2305455981120716800L, -34359498752L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_20 = antlr.BitSet(mk_tokenSet_20())

### generate bit set
def mk_tokenSet_21(): 
    ### var1
    data = [ 575897802350002176L, -4611686018426757120L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_21 = antlr.BitSet(mk_tokenSet_21())

### generate bit set
def mk_tokenSet_22(): 
    ### var1
    data = [ 1152921504606846976L, 0L, 3L, 0L, 0L, 0L]
    return data
_tokenSet_22 = antlr.BitSet(mk_tokenSet_22())

### generate bit set
def mk_tokenSet_23(): 
    ### var1
    data = [ 575897802350002176L, 106496L, 65472L, 0L, 0L, 0L]
    return data
_tokenSet_23 = antlr.BitSet(mk_tokenSet_23())

### generate bit set
def mk_tokenSet_24(): 
    ### var1
    data = [ 2305737456097427456L, -34358957056L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_24 = antlr.BitSet(mk_tokenSet_24())

### generate bit set
def mk_tokenSet_25(): 
    ### var1
    data = [ 1729839653747425280L, -34359063552L, 15L, 0L, 0L, 0L]
    return data
_tokenSet_25 = antlr.BitSet(mk_tokenSet_25())

### generate bit set
def mk_tokenSet_26(): 
    ### var1
    data = [ -101704825569280L, -25770070145L, 65535L, 0L, 0L, 0L]
    return data
_tokenSet_26 = antlr.BitSet(mk_tokenSet_26())

### generate bit set
def mk_tokenSet_27(): 
    ### var1
    data = [ 576179277326712832L, -4611686018427281408L, 65532L, 0L, 0L, 0L]
    return data
_tokenSet_27 = antlr.BitSet(mk_tokenSet_27())
    
