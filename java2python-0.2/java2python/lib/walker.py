### $ANTLR 2.7.7 (20070206): "walker.g" -> "walker.py"$
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
LITERAL_const = 151

### user code>>>

### user code<<<

class Walker(antlr.TreeParser):
    
    # ctor ..
    def __init__(self, *args, **kwargs):
        antlr.TreeParser.__init__(self, *args, **kwargs)
        self.tokenNames = _tokenNames
    
    ### user action >>>
    ### user action <<<
    def walk(self, _t,
        source
    ):    
        
        walk_AST_in = None
        if _t != antlr.ASTNULL:
            walk_AST_in = _t
        try:      ## for error handling
            pass
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [PACKAGE_DEF]:
                pass
                pkg=self.package_def(_t, source)
                _t = self._retTree
            elif la1 and la1 in [3,CLASS_DEF,INTERFACE_DEF,IMPORT]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==IMPORT):
                    pass
                    imp=self.import_def(_t, source)
                    _t = self._retTree
                else:
                    break
                
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==CLASS_DEF or _t.getType()==INTERFACE_DEF):
                    pass
                    typ=self.type_def(_t, source)
                    _t = self._retTree
                else:
                    break
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def package_def(self, _t,
        block
    ):    
        defn = None
        
        package_def_AST_in = None
        if _t != antlr.ASTNULL:
            package_def_AST_in = _t
        try:      ## for error handling
            pass
            _t8 = _t
            tmp1_AST_in = _t
            self.match(_t,PACKAGE_DEF)
            _t = _t.getFirstChild()
            defn=self.identifier(_t, block)
            _t = self._retTree
            _t = _t8
            _t = _t.getNextSibling()
            # block.addSource(("### package %s", defn))
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return defn
    
    def import_def(self, _t,
        block
    ):    
        defn = None
        
        import_def_AST_in = None
        if _t != antlr.ASTNULL:
            import_def_AST_in = _t
        try:      ## for error handling
            pass
            _t10 = _t
            tmp2_AST_in = _t
            self.match(_t,IMPORT)
            _t = _t.getFirstChild()
            defn=self.identifier_star(_t, block)
            _t = self._retTree
            _t = _t10
            _t = _t.getNextSibling()
            # block.addSource(("### import %s", defn))
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return defn
    
    def type_def(self, _t,
        block
    ):    
        klass = block.newClass()
        
        type_def_AST_in = None
        if _t != antlr.ASTNULL:
            type_def_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [CLASS_DEF]:
                pass
                _t12 = _t
                tmp3_AST_in = _t
                self.match(_t,CLASS_DEF)
                _t = _t.getFirstChild()
                self.modifiers(_t, klass)
                _t = self._retTree
                name=self.identifier(_t, klass)
                _t = self._retTree
                klass.setName(name)
                ext_clause=self.extends_clause(_t, klass)
                _t = self._retTree
                imp_clause=self.implements_clause(_t, klass)
                _t = self._retTree
                self.obj_block(_t, klass)
                _t = self._retTree
                _t = _t12
                _t = _t.getNextSibling()
            elif la1 and la1 in [INTERFACE_DEF]:
                pass
                _t13 = _t
                tmp4_AST_in = _t
                self.match(_t,INTERFACE_DEF)
                _t = _t.getFirstChild()
                self.modifiers(_t, klass)
                _t = self._retTree
                name=self.identifier(_t, klass)
                _t = self._retTree
                klass.setName(name)
                ext_clause=self.extends_clause(_t, klass)
                _t = self._retTree
                self.interface_block(_t, klass)
                _t = self._retTree
                _t = _t13
                _t = _t.getNextSibling()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return klass
    
    def identifier(self, _t,
        block
    ):    
        ident = None
        
        identifier_AST_in = None
        if _t != antlr.ASTNULL:
            identifier_AST_in = _t
        id0 = None
        id1 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [IDENT]:
                pass
                id0 = _t
                self.match(_t,IDENT)
                _t = _t.getNextSibling()
                ident = id0.getText()
            elif la1 and la1 in [DOT]:
                pass
                exp = ()
                _t75 = _t
                tmp5_AST_in = _t
                self.match(_t,DOT)
                _t = _t.getFirstChild()
                exp=self.identifier(_t, block)
                _t = self._retTree
                id1 = _t
                self.match(_t,IDENT)
                _t = _t.getNextSibling()
                _t = _t75
                _t = _t.getNextSibling()
                if exp:
                   ident = ("%s.%s", (("%s", exp), ("%s", id1.getText())))
                else:
                   ident = id1.getText()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return ident
    
    def identifier_star(self, _t,
        block
    ):    
        ident = None
        
        identifier_star_AST_in = None
        if _t != antlr.ASTNULL:
            identifier_star_AST_in = _t
        id0 = None
        id1 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [IDENT]:
                pass
                id0 = _t
                self.match(_t,IDENT)
                _t = _t.getNextSibling()
                ident = id0.getText()
            elif la1 and la1 in [DOT]:
                pass
                exp = ()
                _t77 = _t
                tmp6_AST_in = _t
                self.match(_t,DOT)
                _t = _t.getFirstChild()
                exp=self.identifier(_t, block)
                _t = self._retTree
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [STAR]:
                    pass
                    tmp7_AST_in = _t
                    self.match(_t,STAR)
                    _t = _t.getNextSibling()
                elif la1 and la1 in [IDENT]:
                    pass
                    tmp8_AST_in = _t
                    self.match(_t,IDENT)
                    _t = _t.getNextSibling()
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t77
                _t = _t.getNextSibling()
                if exp and id1:
                   ident = ("%s.%s", (("%s", exp), ("%s", id1.getText())))
                elif exp:
                   ident = ("%s.%s", (("%s", exp), ("%s", "*")))
                else:
                   ident = id1
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return ident
    
    def modifiers(self, _t,
        block, mod=None
    ):    
        
        modifiers_AST_in = None
        if _t != antlr.ASTNULL:
            modifiers_AST_in = _t
        try:      ## for error handling
            pass
            _t21 = _t
            tmp9_AST_in = _t
            self.match(_t,MODIFIERS)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_tokenSet_0.member(_t.getType())):
                    pass
                    mod=self.modifier(_t, block)
                    _t = self._retTree
                    if mod:
                       block.addModifier(mod)
                else:
                    break
                
            _t = _t21
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def extends_clause(self, _t,
        block
    ):    
        clause = None
        
        extends_clause_AST_in = None
        if _t != antlr.ASTNULL:
            extends_clause_AST_in = _t
        try:      ## for error handling
            pass
            _t26 = _t
            tmp10_AST_in = _t
            self.match(_t,EXTENDS_CLAUSE)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==IDENT or _t.getType()==DOT):
                    pass
                    clause=self.identifier(_t, block)
                    _t = self._retTree
                else:
                    break
                
            _t = _t26
            _t = _t.getNextSibling()
            block.addBaseClass(clause)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return clause
    
    def implements_clause(self, _t,
        block
    ):    
        clause = None
        
        implements_clause_AST_in = None
        if _t != antlr.ASTNULL:
            implements_clause_AST_in = _t
        try:      ## for error handling
            pass
            _t30 = _t
            tmp11_AST_in = _t
            self.match(_t,IMPLEMENTS_CLAUSE)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==IDENT or _t.getType()==DOT):
                    pass
                    clause=self.identifier(_t, block)
                    _t = self._retTree
                else:
                    break
                
            _t = _t30
            _t = _t.getNextSibling()
            block.addBaseClass(clause)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return clause
    
    def obj_block(self, _t,
        block
    ):    
        
        obj_block_AST_in = None
        if _t != antlr.ASTNULL:
            obj_block_AST_in = _t
        try:      ## for error handling
            pass
            _t38 = _t
            tmp12_AST_in = _t
            self.match(_t,OBJBLOCK)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [CTOR_DEF]:
                    pass
                    self.ctor_def(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [METHOD_DEF]:
                    pass
                    self.method_def(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [VARIABLE_DEF]:
                    pass
                    self.variable_def(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [CLASS_DEF,INTERFACE_DEF]:
                    pass
                    typ=self.type_def(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [STATIC_INIT]:
                    pass
                    _t40 = _t
                    tmp13_AST_in = _t
                    self.match(_t,STATIC_INIT)
                    _t = _t.getFirstChild()
                    self.statement_list(_t, block)
                    _t = self._retTree
                    _t = _t40
                    _t = _t.getNextSibling()
                elif la1 and la1 in [INSTANCE_INIT]:
                    pass
                    _t41 = _t
                    tmp14_AST_in = _t
                    self.match(_t,INSTANCE_INIT)
                    _t = _t.getFirstChild()
                    self.statement_list(_t, block)
                    _t = self._retTree
                    _t = _t41
                    _t = _t.getNextSibling()
                else:
                        break
                    
            _t = _t38
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def interface_block(self, _t,
        block
    ):    
        
        interface_block_AST_in = None
        if _t != antlr.ASTNULL:
            interface_block_AST_in = _t
        try:      ## for error handling
            pass
            _t34 = _t
            tmp15_AST_in = _t
            self.match(_t,OBJBLOCK)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [METHOD_DEF]:
                    pass
                    self.method_decl(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [VARIABLE_DEF]:
                    pass
                    self.variable_def(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [CLASS_DEF,INTERFACE_DEF]:
                    pass
                    typ=self.type_def(_t, block)
                    _t = self._retTree
                else:
                        break
                    
            _t = _t34
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def type_spec(self, _t,
        block
    ):    
        spec = None
        
        type_spec_AST_in = None
        if _t != antlr.ASTNULL:
            type_spec_AST_in = _t
        t0 = None
        try:      ## for error handling
            pass
            _t15 = _t
            t0 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
            self.match(_t,TYPE)
            _t = _t.getFirstChild()
            self.type_spec_array(_t, block)
            _t = self._retTree
            _t = _t15
            _t = _t.getNextSibling()
            first = t0.getFirstChild()
            value = first.getText()
            if value == ".":
               seq = []
               next = first.getFirstChild()
               while next:
                   seq.append(next.getText())
                   next = next.getNextSibling()
               value = str.join(".", seq)
            try:
               spec = block.config.combined("typeTypeMap")[value]
            except (KeyError, ):
               spec = value
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return spec
    
    def type_spec_array(self, _t,
        block
    ):    
        
        type_spec_array_AST_in = None
        if _t != antlr.ASTNULL:
            type_spec_array_AST_in = _t
        ad0 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [ARRAY_DECLARATOR]:
                pass
                _t17 = _t
                ad0 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
                self.match(_t,ARRAY_DECLARATOR)
                _t = _t.getFirstChild()
                self.type_spec_array(_t, block)
                _t = self._retTree
                _t = _t17
                _t = _t.getNextSibling()
            elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double,IDENT,DOT]:
                pass
                spec=self.type(_t, block)
                _t = self._retTree
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def type(self, _t,
        block
    ):    
        typ = None
        
        type_AST_in = None
        if _t != antlr.ASTNULL:
            type_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [IDENT,DOT]:
                pass
                typ=self.identifier(_t, block)
                _t = self._retTree
                block.type = typ
            elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double]:
                pass
                typ=self.builtin_type(_t, block)
                _t = self._retTree
                block.type = typ
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return typ
    
    def builtin_type(self, _t,
        block
    ):    
        typ = None
        
        builtin_type_AST_in = None
        if _t != antlr.ASTNULL:
            builtin_type_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [LITERAL_void]:
                pass
                tmp16_AST_in = _t
                self.match(_t,LITERAL_void)
                _t = _t.getNextSibling()
                typ = "None"
            elif la1 and la1 in [LITERAL_boolean]:
                pass
                tmp17_AST_in = _t
                self.match(_t,LITERAL_boolean)
                _t = _t.getNextSibling()
                typ = "bool"
            elif la1 and la1 in [LITERAL_byte]:
                pass
                tmp18_AST_in = _t
                self.match(_t,LITERAL_byte)
                _t = _t.getNextSibling()
                typ = "str"
            elif la1 and la1 in [LITERAL_char]:
                pass
                tmp19_AST_in = _t
                self.match(_t,LITERAL_char)
                _t = _t.getNextSibling()
                typ = "str"
            elif la1 and la1 in [LITERAL_short]:
                pass
                tmp20_AST_in = _t
                self.match(_t,LITERAL_short)
                _t = _t.getNextSibling()
                typ = "int"
            elif la1 and la1 in [LITERAL_int]:
                pass
                tmp21_AST_in = _t
                self.match(_t,LITERAL_int)
                _t = _t.getNextSibling()
                typ = "int"
            elif la1 and la1 in [LITERAL_float]:
                pass
                tmp22_AST_in = _t
                self.match(_t,LITERAL_float)
                _t = _t.getNextSibling()
                typ = "float"
            elif la1 and la1 in [LITERAL_long]:
                pass
                tmp23_AST_in = _t
                self.match(_t,LITERAL_long)
                _t = _t.getNextSibling()
                typ = "long"
            elif la1 and la1 in [LITERAL_double]:
                pass
                tmp24_AST_in = _t
                self.match(_t,LITERAL_double)
                _t = _t.getNextSibling()
                typ = "float"
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return typ
    
    def modifier(self, _t,
        block
    ):    
        mod = None
        
        modifier_AST_in = None
        if _t != antlr.ASTNULL:
            modifier_AST_in = _t
        pri0 = None
        pub0 = None
        pro0 = None
        sta0 = None
        tra0 = None
        fin0 = None
        abt0 = None
        nat0 = None
        ths0 = None
        syn0 = None
        con0 = None
        vol0 = None
        sfp0 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [LITERAL_private]:
                pass
                pri0 = _t
                self.match(_t,LITERAL_private)
                _t = _t.getNextSibling()
                mod = pri0.getText()
            elif la1 and la1 in [LITERAL_public]:
                pass
                pub0 = _t
                self.match(_t,LITERAL_public)
                _t = _t.getNextSibling()
                mod = pub0.getText()
            elif la1 and la1 in [LITERAL_protected]:
                pass
                pro0 = _t
                self.match(_t,LITERAL_protected)
                _t = _t.getNextSibling()
                mod = pro0.getText()
            elif la1 and la1 in [LITERAL_static]:
                pass
                sta0 = _t
                self.match(_t,LITERAL_static)
                _t = _t.getNextSibling()
                mod = sta0.getText()
            elif la1 and la1 in [LITERAL_transient]:
                pass
                tra0 = _t
                self.match(_t,LITERAL_transient)
                _t = _t.getNextSibling()
                mod = tra0.getText()
            elif la1 and la1 in [FINAL]:
                pass
                fin0 = _t
                self.match(_t,FINAL)
                _t = _t.getNextSibling()
                mod = fin0.getText()
            elif la1 and la1 in [ABSTRACT]:
                pass
                abt0 = _t
                self.match(_t,ABSTRACT)
                _t = _t.getNextSibling()
                mod = abt0.getText()
            elif la1 and la1 in [LITERAL_native]:
                pass
                nat0 = _t
                self.match(_t,LITERAL_native)
                _t = _t.getNextSibling()
                mod = nat0.getText()
            elif la1 and la1 in [LITERAL_threadsafe]:
                pass
                ths0 = _t
                self.match(_t,LITERAL_threadsafe)
                _t = _t.getNextSibling()
                mod = ths0.getText()
            elif la1 and la1 in [LITERAL_synchronized]:
                pass
                syn0 = _t
                self.match(_t,LITERAL_synchronized)
                _t = _t.getNextSibling()
                mod = syn0.getText()
            elif la1 and la1 in [LITERAL_const]:
                pass
                con0 = _t
                self.match(_t,LITERAL_const)
                _t = _t.getNextSibling()
                mod = con0.getText()
            elif la1 and la1 in [LITERAL_volatile]:
                pass
                vol0 = _t
                self.match(_t,LITERAL_volatile)
                _t = _t.getNextSibling()
                mod = vol0.getText()
            elif la1 and la1 in [STRICTFP]:
                pass
                sfp0 = _t
                self.match(_t,STRICTFP)
                _t = _t.getNextSibling()
                mod = sfp0.getText()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return mod
    
    def method_decl(self, _t,
        block
    ):    
        
        method_decl_AST_in = None
        if _t != antlr.ASTNULL:
            method_decl_AST_in = _t
        try:      ## for error handling
            pass
            meth = block.newMethod()
            meth.addSource("raise NotImplementedError()")
            _t47 = _t
            tmp25_AST_in = _t
            self.match(_t,METHOD_DEF)
            _t = _t.getFirstChild()
            self.modifiers(_t, meth)
            _t = self._retTree
            typ=self.type_spec(_t, meth)
            _t = self._retTree
            self.method_head(_t, meth)
            _t = self._retTree
            _t = _t47
            _t = _t.getNextSibling()
            meth.type = typ
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def variable_def(self, _t,
        block
    ):    
        
        variable_def_AST_in = None
        if _t != antlr.ASTNULL:
            variable_def_AST_in = _t
        try:      ## for error handling
            pass
            _t57 = _t
            tmp26_AST_in = _t
            self.match(_t,VARIABLE_DEF)
            _t = _t.getFirstChild()
            self.modifiers(_t, block)
            _t = self._retTree
            typ=self.type_spec(_t, block)
            _t = self._retTree
            dec=self.var_decl(_t, block)
            _t = self._retTree
            val=self.var_init(_t, block)
            _t = self._retTree
            _t = _t57
            _t = _t.getNextSibling()
            block.addVariable(dec[1])
            if val == block.emptyAssign:
               val = ("%s", block.config.combined("typeValueMap").get(typ, "%s()" % typ))
            block.addSource( ("%s = %s", (dec, val)) )
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def ctor_def(self, _t,
        block
    ):    
        
        ctor_def_AST_in = None
        if _t != antlr.ASTNULL:
            ctor_def_AST_in = _t
        try:      ## for error handling
            pass
            meth = block.newMethod()
            _t44 = _t
            tmp27_AST_in = _t
            self.match(_t,CTOR_DEF)
            _t = _t.getFirstChild()
            self.modifiers(_t, meth)
            _t = self._retTree
            self.method_head(_t, meth, "__init__")
            _t = self._retTree
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [SLIST]:
                pass
                self.statement_list(_t, meth)
                _t = self._retTree
            elif la1 and la1 in [3]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            _t = _t44
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def method_def(self, _t,
        block
    ):    
        
        method_def_AST_in = None
        if _t != antlr.ASTNULL:
            method_def_AST_in = _t
        try:      ## for error handling
            pass
            meth = block.newMethod()
            _t54 = _t
            tmp28_AST_in = _t
            self.match(_t,METHOD_DEF)
            _t = _t.getFirstChild()
            self.modifiers(_t, meth)
            _t = self._retTree
            typ=self.type_spec(_t, block)
            _t = self._retTree
            self.method_head(_t, meth)
            _t = self._retTree
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [SLIST]:
                pass
                self.statement_list(_t, meth)
                _t = self._retTree
            elif la1 and la1 in [3]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            _t = _t54
            _t = _t.getNextSibling()
            meth.type = typ
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def statement_list(self, _t,
        block
    ):    
        
        statement_list_AST_in = None
        if _t != antlr.ASTNULL:
            statement_list_AST_in = _t
        s1 = None
        try:      ## for error handling
            pass
            _t80 = _t
            tmp29_AST_in = _t
            self.match(_t,SLIST)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_tokenSet_1.member(_t.getType())):
                    pass
                    s1 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
                    self.statement(_t, block)
                    _t = self._retTree
                else:
                    break
                
            _t = _t80
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def method_head(self, _t,
        meth, name=None
    ):    
        
        method_head_AST_in = None
        if _t != antlr.ASTNULL:
            method_head_AST_in = _t
        try:      ## for error handling
            pass
            ident=self.identifier(_t, meth)
            _t = self._retTree
            meth.setName(name if name else ident)
            meth.parent.addVariable(meth.name)
            _t49 = _t
            tmp30_AST_in = _t
            self.match(_t,PARAMETERS)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==PARAMETER_DEF):
                    pass
                    self.parameter_def(_t, meth)
                    _t = self._retTree
                else:
                    break
                
            _t = _t49
            _t = _t.getNextSibling()
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [LITERAL_throws]:
                pass
                self.throws_clause(_t, meth)
                _t = self._retTree
            elif la1 and la1 in [3,SLIST]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def parameter_def(self, _t,
        meth, exc=False
    ):    
        
        parameter_def_AST_in = None
        if _t != antlr.ASTNULL:
            parameter_def_AST_in = _t
        pd0 = None
        try:      ## for error handling
            pass
            _t59 = _t
            pd0 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
            self.match(_t,PARAMETER_DEF)
            _t = _t.getFirstChild()
            self.modifiers(_t, meth)
            _t = self._retTree
            ptype=self.type_spec(_t, meth)
            _t = self._retTree
            ident=self.identifier(_t, meth)
            _t = self._retTree
            _t = _t59
            _t = _t.getNextSibling()
            if exc:
               ptype = meth.alternateName(ptype, "exceptionTypeMapping")
               meth.setExpression(("(%s, ), %s", (("%s", ptype), ("%s", ident))))
            else:
               meth.addParameter(ptype, ident)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def throws_clause(self, _t,
        block, ident=None
    ):    
        
        throws_clause_AST_in = None
        if _t != antlr.ASTNULL:
            throws_clause_AST_in = _t
        try:      ## for error handling
            pass
            _t71 = _t
            tmp31_AST_in = _t
            self.match(_t,LITERAL_throws)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==IDENT or _t.getType()==DOT):
                    pass
                    ident=self.identifier(_t, block)
                    _t = self._retTree
                else:
                    break
                
            _t = _t71
            _t = _t.getNextSibling()
            if ident:
               block.addModifier("throws %s" % ident)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def var_decl(self, _t,
        block
    ):    
        decl = None
        
        var_decl_AST_in = None
        if _t != antlr.ASTNULL:
            var_decl_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [IDENT,DOT]:
                pass
                ident=self.identifier(_t, block)
                _t = self._retTree
                decl = ("%s", ident)
            elif la1 and la1 in [LBRACK]:
                pass
                tmp32_AST_in = _t
                self.match(_t,LBRACK)
                _t = _t.getNextSibling()
                inner=self.var_decl(_t, block)
                _t = self._retTree
                decl = ("(%s)", inner)
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return decl
    
    def var_init(self, _t,
        block
    ):    
        init = block.emptyAssign
        
        var_init_AST_in = None
        if _t != antlr.ASTNULL:
            var_init_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [ASSIGN]:
                pass
                _t64 = _t
                tmp33_AST_in = _t
                self.match(_t,ASSIGN)
                _t = _t.getFirstChild()
                init=self.initializer(_t, block)
                _t = self._retTree
                _t = _t64
                _t = _t.getNextSibling()
            elif la1 and la1 in [3]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return init
    
    def obj_init(self, _t,
        block
    ):    
        
        obj_init_AST_in = None
        if _t != antlr.ASTNULL:
            obj_init_AST_in = _t
        try:      ## for error handling
            pass
            _t61 = _t
            tmp34_AST_in = _t
            self.match(_t,INSTANCE_INIT)
            _t = _t.getFirstChild()
            self.statement_list(_t, block)
            _t = self._retTree
            _t = _t61
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def initializer(self, _t,
        block
    ):    
        init = None
        
        initializer_AST_in = None
        if _t != antlr.ASTNULL:
            initializer_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [EXPR]:
                pass
                init=self.expression(_t, block, False)
                _t = self._retTree
            elif la1 and la1 in [ARRAY_INIT]:
                pass
                init=self.array_initializer(_t, block)
                _t = self._retTree
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return init
    
    def expression(self, _t,
        block, append=True
    ):    
        exp = None
        
        expression_AST_in = None
        if _t != antlr.ASTNULL:
            expression_AST_in = _t
        try:      ## for error handling
            pass
            _t126 = _t
            tmp35_AST_in = _t
            self.match(_t,EXPR)
            _t = _t.getFirstChild()
            exp=self.expr(_t, block)
            _t = self._retTree
            _t = _t126
            _t = _t.getNextSibling()
            if append:
               block.addSource(exp)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return exp
    
    def array_initializer(self, _t,
        block
    ):    
        init = None
        
        array_initializer_AST_in = None
        if _t != antlr.ASTNULL:
            array_initializer_AST_in = _t
        try:      ## for error handling
            pass
            _t67 = _t
            tmp36_AST_in = _t
            self.match(_t,ARRAY_INIT)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==EXPR or _t.getType()==ARRAY_INIT):
                    pass
                    init=self.initializer(_t, block)
                    _t = self._retTree
                else:
                    break
                
            _t = _t67
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return init
    
    def statement(self, _t,
        block
    ):    
        
        statement_AST_in = None
        if _t != antlr.ASTNULL:
            statement_AST_in = _t
        break_label = None
        continue_label = None
        c = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [CLASS_DEF,INTERFACE_DEF]:
                pass
                typ=self.type_def(_t, block)
                _t = self._retTree
            elif la1 and la1 in [VARIABLE_DEF]:
                pass
                self.variable_def(_t, block)
                _t = self._retTree
            elif la1 and la1 in [EXPR]:
                pass
                exp=self.expression(_t, block)
                _t = self._retTree
            elif la1 and la1 in [LABELED_STAT]:
                pass
                _t84 = _t
                tmp37_AST_in = _t
                self.match(_t,LABELED_STAT)
                _t = _t.getFirstChild()
                tmp38_AST_in = _t
                self.match(_t,IDENT)
                _t = _t.getNextSibling()
                self.statement(_t, block)
                _t = self._retTree
                _t = _t84
                _t = _t.getNextSibling()
            elif la1 and la1 in [LITERAL_if]:
                pass
                if_stat = block.newStatement("if")
                else_stat = block.newStatement("else")
                _t85 = _t
                tmp39_AST_in = _t
                self.match(_t,LITERAL_if)
                _t = _t.getFirstChild()
                if_expr=self.expression(_t, if_stat, False)
                _t = self._retTree
                self.statement(_t, if_stat)
                _t = self._retTree
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [SLIST,VARIABLE_DEF,CLASS_DEF,INTERFACE_DEF,LABELED_STAT,EXPR,EMPTY_STAT,SUPER_CTOR_CALL,CTOR_CALL,LITERAL_if,LITERAL_for,LITERAL_while,LITERAL_do,LITERAL_break,LITERAL_continue,LITERAL_return,LITERAL_switch,LITERAL_throw,LITERAL_try]:
                    pass
                    self.statement(_t, else_stat)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t85
                _t = _t.getNextSibling()
                if_stat.setExpression(if_expr)
            elif la1 and la1 in [LITERAL_for]:
                pass
                block.addComment("for-while")
                for_init, for_stat = block.newFor()
                _t87 = _t
                tmp40_AST_in = _t
                self.match(_t,LITERAL_for)
                _t = _t.getFirstChild()
                _t88 = _t
                tmp41_AST_in = _t
                self.match(_t,FOR_INIT)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [VARIABLE_DEF]:
                    pass
                    _cnt91= 0
                    while True:
                        if not _t:
                            _t = antlr.ASTNULL
                        if (_t.getType()==VARIABLE_DEF):
                            pass
                            self.variable_def(_t, for_init)
                            _t = self._retTree
                        else:
                            break
                        
                        _cnt91 += 1
                    if _cnt91 < 1:
                        raise antlr.NoViableAltException(_t)
                elif la1 and la1 in [ELIST]:
                    pass
                    for_exp=self.expr_list(_t, for_init)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t88
                _t = _t.getNextSibling()
                _t92 = _t
                tmp42_AST_in = _t
                self.match(_t,FOR_CONDITION)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [EXPR]:
                    pass
                    for_cond=self.expression(_t, for_stat, False)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t92
                _t = _t.getNextSibling()
                _t94 = _t
                tmp43_AST_in = _t
                self.match(_t,FOR_ITERATOR)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [ELIST]:
                    pass
                    for_iter=self.expr_list(_t, for_stat, False)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t94
                _t = _t.getNextSibling()
                self.statement(_t, for_stat)
                _t = self._retTree
                _t = _t87
                _t = _t.getNextSibling()
                for_stat.setExpression(("%s", for_cond))
                for_stat.addSource(("%s", for_iter))
            elif la1 and la1 in [LITERAL_while]:
                pass
                while_stat = block.newStatement("while")
                _t96 = _t
                tmp44_AST_in = _t
                self.match(_t,LITERAL_while)
                _t = _t.getFirstChild()
                while_expr=self.expression(_t, block, False)
                _t = self._retTree
                self.statement(_t, while_stat)
                _t = self._retTree
                _t = _t96
                _t = _t.getNextSibling()
                while_stat.setExpression(while_expr)
            elif la1 and la1 in [LITERAL_do]:
                pass
                _t97 = _t
                tmp45_AST_in = _t
                self.match(_t,LITERAL_do)
                _t = _t.getFirstChild()
                self.statement(_t, block)
                _t = self._retTree
                do_exp=self.expression(_t, block)
                _t = self._retTree
                _t = _t97
                _t = _t.getNextSibling()
                raise NotImplementedError("do statement")
            elif la1 and la1 in [LITERAL_break]:
                pass
                break_stat = block.newStatement("break")
                break_label = block.missingValue
                _t98 = _t
                tmp46_AST_in = _t
                self.match(_t,LITERAL_break)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [IDENT]:
                    pass
                    break_label = _t
                    self.match(_t,IDENT)
                    _t = _t.getNextSibling()
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t98
                _t = _t.getNextSibling()
                if break_label is not block.missingValue:
                   raise NotImplementedError("break with label")
            elif la1 and la1 in [LITERAL_continue]:
                pass
                continue_stat = block.newStatement("continue")
                continue_label = block.missingValue
                _t100 = _t
                tmp47_AST_in = _t
                self.match(_t,LITERAL_continue)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [IDENT]:
                    pass
                    continue_label = _t
                    self.match(_t,IDENT)
                    _t = _t.getNextSibling()
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t100
                _t = _t.getNextSibling()
                if continue_label is not block.missingValue:
                   raise NotImplementedError("continue with label")
            elif la1 and la1 in [LITERAL_return]:
                pass
                return_value = None
                _t102 = _t
                tmp48_AST_in = _t
                self.match(_t,LITERAL_return)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [EXPR]:
                    pass
                    return_value=self.expression(_t, block, False)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t102
                _t = _t.getNextSibling()
                if return_value in (None, ("%s", "None")):
                   block.addSource("return")
                else:
                   block.addSource(("return %s", return_value))
            elif la1 and la1 in [LITERAL_switch]:
                pass
                switch_block = block.newSwitch()
                _t104 = _t
                tmp49_AST_in = _t
                self.match(_t,LITERAL_switch)
                _t = _t.getFirstChild()
                switch_expr=self.expression(_t, block, False)
                _t = self._retTree
                while True:
                    if not _t:
                        _t = antlr.ASTNULL
                    if (_t.getType()==CASE_GROUP):
                        pass
                        c = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
                        self.case_group(_t, block, switch_expr)
                        _t = self._retTree
                    else:
                        break
                    
                _t = _t104
                _t = _t.getNextSibling()
                block.fixSwitch(switch_block)
            elif la1 and la1 in [LITERAL_throw]:
                pass
                raise_stat = block.newStatement("raise")
                _t107 = _t
                tmp50_AST_in = _t
                self.match(_t,LITERAL_throw)
                _t = _t.getFirstChild()
                raise_exp=self.expression(_t, block)
                _t = self._retTree
                _t = _t107
                _t = _t.getNextSibling()
                raise_stat.setExpression(raise_exp)
            elif la1 and la1 in [LITERAL_try]:
                pass
                self.try_block(_t, block)
                _t = self._retTree
            elif la1 and la1 in [SUPER_CTOR_CALL,CTOR_CALL]:
                pass
                self.ctor_call(_t, block)
                _t = self._retTree
            elif la1 and la1 in [SLIST]:
                pass
                self.statement_list(_t, block)
                _t = self._retTree
            elif la1 and la1 in [EMPTY_STAT]:
                pass
                tmp51_AST_in = _t
                self.match(_t,EMPTY_STAT)
                _t = _t.getNextSibling()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def expr_list(self, _t,
        block, append=False
    ):    
        seq = None
        
        expr_list_AST_in = None
        if _t != antlr.ASTNULL:
            expr_list_AST_in = _t
        try:      ## for error handling
            pass
            _t122 = _t
            tmp52_AST_in = _t
            self.match(_t,ELIST)
            _t = _t.getFirstChild()
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==EXPR):
                    pass
                    exp=self.expression(_t, block, append)
                    _t = self._retTree
                    if seq:
                       seq = ("%s, %s", (("%s", seq), ("%s", exp)))
                    else:
                       seq = ("%s", exp)
                else:
                    break
                
            _t = _t122
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return seq
    
    def case_group(self, _t,
        block, switch_expr
    ):    
        
        case_group_AST_in = None
        if _t != antlr.ASTNULL:
            case_group_AST_in = _t
        try:      ## for error handling
            pass
            other = block.newStatement("elif")
            right = block.missingValue
            _t109 = _t
            tmp53_AST_in = _t
            self.match(_t,CASE_GROUP)
            _t = _t.getFirstChild()
            _cnt112= 0
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [LITERAL_case]:
                    pass
                    _t111 = _t
                    tmp54_AST_in = _t
                    self.match(_t,LITERAL_case)
                    _t = _t.getFirstChild()
                    right=self.expression(_t, other, False)
                    _t = self._retTree
                    _t = _t111
                    _t = _t.getNextSibling()
                elif la1 and la1 in [LITERAL_default]:
                    pass
                    tmp55_AST_in = _t
                    self.match(_t,LITERAL_default)
                    _t = _t.getNextSibling()
                else:
                        break
                    
                _cnt112 += 1
            if _cnt112 < 1:
                raise antlr.NoViableAltException(_t)
            self.statement_list(_t, other)
            _t = self._retTree
            _t = _t109
            _t = _t.getNextSibling()
            if right is block.missingValue:
               other.setName("else")
               other.setExpression(None)
            else:
               other.setExpression(("%s == %s", (switch_expr, ("%s", right))))
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def try_block(self, _t,
        block
    ):    
        
        try_block_AST_in = None
        if _t != antlr.ASTNULL:
            try_block_AST_in = _t
        try_stat = block.newStatement("try")
        except_stat = block.newStatement("except")
        finally_stat = block.newStatement("finally")
        try:      ## for error handling
            pass
            _t114 = _t
            tmp56_AST_in = _t
            self.match(_t,LITERAL_try)
            _t = _t.getFirstChild()
            self.statement_list(_t, try_stat)
            _t = self._retTree
            while True:
                if not _t:
                    _t = antlr.ASTNULL
                if (_t.getType()==LITERAL_catch):
                    pass
                    self.handler(_t, except_stat)
                    _t = self._retTree
                else:
                    break
                
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [LITERAL_finally]:
                pass
                _t118 = _t
                tmp57_AST_in = _t
                self.match(_t,LITERAL_finally)
                _t = _t.getFirstChild()
                self.statement_list(_t, finally_stat)
                _t = self._retTree
                _t = _t118
                _t = _t.getNextSibling()
            elif la1 and la1 in [3]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            _t = _t114
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def ctor_call(self, _t,
        block
    ):    
        
        ctor_call_AST_in = None
        if _t != antlr.ASTNULL:
            ctor_call_AST_in = _t
        cn = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [CTOR_CALL]:
                pass
                _t180 = _t
                cn = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
                self.match(_t,CTOR_CALL)
                _t = _t.getFirstChild()
                seq=self.expr_list(_t, block, False)
                _t = self._retTree
                _t = _t180
                _t = _t.getNextSibling()
                name = block.parent.name
                call = ("super(%s, self).__init__(%s)", (("%s", name), ("%s", seq)))
                block.addSource(call)
            elif la1 and la1 in [SUPER_CTOR_CALL]:
                pass
                _t181 = _t
                tmp58_AST_in = _t
                self.match(_t,SUPER_CTOR_CALL)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [ELIST]:
                    pass
                    el0=self.expr_list(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [TYPE,TYPECAST,INDEX_OP,METHOD_CALL,SUPER_CTOR_CALL,CTOR_CALL,IDENT,DOT,LITERAL_this,LITERAL_super,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                    pass
                    p=self.primary_expr(_t, block)
                    _t = self._retTree
                    el2=self.expr_list(_t, block)
                    _t = self._retTree
                else:
                        raise antlr.NoViableAltException(_t)
                    
                raise NotImplementedError("SUPER_CTOR_CALL")
                _t = _t181
                _t = _t.getNextSibling()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def handler(self, _t,
        block
    ):    
        
        handler_AST_in = None
        if _t != antlr.ASTNULL:
            handler_AST_in = _t
        try:      ## for error handling
            pass
            _t120 = _t
            tmp59_AST_in = _t
            self.match(_t,LITERAL_catch)
            _t = _t.getFirstChild()
            self.parameter_def(_t, block, True)
            _t = self._retTree
            self.statement_list(_t, block)
            _t = self._retTree
            _t = _t120
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
    
    def expr(self, _t,
        block
    ):    
        exp = block.unknownExpression
        
        expr_AST_in = None
        if _t != antlr.ASTNULL:
            expr_AST_in = _t
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [QUESTION]:
                pass
                _t128 = _t
                tmp60_AST_in = _t
                self.match(_t,QUESTION)
                _t = _t.getFirstChild()
                a0=self.expr(_t, block)
                _t = self._retTree
                b0=self.expr(_t, block)
                _t = self._retTree
                c0=self.expr(_t, block)
                _t = self._retTree
                _t = _t128
                _t = _t.getNextSibling()
                exp = ("%s %s", (("%s", b0), ("%s %s", (("if %s", a0), ("else %s", c0)))))
            elif la1 and la1 in [ASSIGN]:
                pass
                _t129 = _t
                tmp61_AST_in = _t
                self.match(_t,ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t129
                _t = _t.getNextSibling()
                exp = ("%s = %s", (left, right))
            elif la1 and la1 in [PLUS_ASSIGN]:
                pass
                _t130 = _t
                tmp62_AST_in = _t
                self.match(_t,PLUS_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t130
                _t = _t.getNextSibling()
                exp = ("%s += %s", (left, right))
            elif la1 and la1 in [MINUS_ASSIGN]:
                pass
                _t131 = _t
                tmp63_AST_in = _t
                self.match(_t,MINUS_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t131
                _t = _t.getNextSibling()
                exp = ("%s -= %s", (left, right))
            elif la1 and la1 in [STAR_ASSIGN]:
                pass
                _t132 = _t
                tmp64_AST_in = _t
                self.match(_t,STAR_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t132
                _t = _t.getNextSibling()
                exp = ("%s *= %s", (left, right))
            elif la1 and la1 in [DIV_ASSIGN]:
                pass
                _t133 = _t
                tmp65_AST_in = _t
                self.match(_t,DIV_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t133
                _t = _t.getNextSibling()
                exp = ("%s /= %s", (left, right))
            elif la1 and la1 in [MOD_ASSIGN]:
                pass
                _t134 = _t
                tmp66_AST_in = _t
                self.match(_t,MOD_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t134
                _t = _t.getNextSibling()
                exp = ("%s %%= %s", (left, right))
            elif la1 and la1 in [SR_ASSIGN]:
                pass
                _t135 = _t
                tmp67_AST_in = _t
                self.match(_t,SR_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t135
                _t = _t.getNextSibling()
                exp = ("%s >>= %s", (left, right))
            elif la1 and la1 in [BSR_ASSIGN]:
                pass
                _t136 = _t
                tmp68_AST_in = _t
                self.match(_t,BSR_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t136
                _t = _t.getNextSibling()
                raise NotImplementedError("BSR_ASSIGN")
            elif la1 and la1 in [SL_ASSIGN]:
                pass
                _t137 = _t
                tmp69_AST_in = _t
                self.match(_t,SL_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t137
                _t = _t.getNextSibling()
                exp = ("%s <<= %s", (left, right))
            elif la1 and la1 in [BAND_ASSIGN]:
                pass
                _t138 = _t
                tmp70_AST_in = _t
                self.match(_t,BAND_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t138
                _t = _t.getNextSibling()
                exp = ("%s &= %s", (left, right))
            elif la1 and la1 in [BXOR_ASSIGN]:
                pass
                _t139 = _t
                tmp71_AST_in = _t
                self.match(_t,BXOR_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t139
                _t = _t.getNextSibling()
                exp = ("%s ^= %s", (left, right))
            elif la1 and la1 in [BOR_ASSIGN]:
                pass
                _t140 = _t
                tmp72_AST_in = _t
                self.match(_t,BOR_ASSIGN)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t140
                _t = _t.getNextSibling()
                exp = ("%s |= %s", (left, right))
            elif la1 and la1 in [LOR]:
                pass
                _t141 = _t
                tmp73_AST_in = _t
                self.match(_t,LOR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t141
                _t = _t.getNextSibling()
                exp = ("%s or %s", (left, right))
            elif la1 and la1 in [LAND]:
                pass
                _t142 = _t
                tmp74_AST_in = _t
                self.match(_t,LAND)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t142
                _t = _t.getNextSibling()
                exp = ("%s and %s", (left, right))
            elif la1 and la1 in [BOR]:
                pass
                _t143 = _t
                tmp75_AST_in = _t
                self.match(_t,BOR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t143
                _t = _t.getNextSibling()
                exp = ("%s | %s", (left, right))
            elif la1 and la1 in [BXOR]:
                pass
                _t144 = _t
                tmp76_AST_in = _t
                self.match(_t,BXOR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t144
                _t = _t.getNextSibling()
                exp = ("%s ^ %s", (left, right))
            elif la1 and la1 in [BAND]:
                pass
                _t145 = _t
                tmp77_AST_in = _t
                self.match(_t,BAND)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t145
                _t = _t.getNextSibling()
                exp = ("%s & %s", (left, right))
            elif la1 and la1 in [NOT_EQUAL]:
                pass
                _t146 = _t
                tmp78_AST_in = _t
                self.match(_t,NOT_EQUAL)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t146
                _t = _t.getNextSibling()
                if right in ("None", (("%s", "None"))):
                   exp = ("%s is not %s", (left, right))
                else:
                   exp = ("(%s != %s)", (left, right))
            elif la1 and la1 in [EQUAL]:
                pass
                _t147 = _t
                tmp79_AST_in = _t
                self.match(_t,EQUAL)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t147
                _t = _t.getNextSibling()
                if right in ("None", (("%s", "None"))):
                   exp = ("%s is %s", (left, right))
                else:
                   exp = ("(%s == %s)", (left, right))
            elif la1 and la1 in [LT]:
                pass
                _t148 = _t
                tmp80_AST_in = _t
                self.match(_t,LT)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t148
                _t = _t.getNextSibling()
                exp = ("%s < %s", (left, right))
            elif la1 and la1 in [GT]:
                pass
                _t149 = _t
                tmp81_AST_in = _t
                self.match(_t,GT)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t149
                _t = _t.getNextSibling()
                exp = ("%s > %s", (left, right))
            elif la1 and la1 in [LE]:
                pass
                _t150 = _t
                tmp82_AST_in = _t
                self.match(_t,LE)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t150
                _t = _t.getNextSibling()
                exp = ("%s <= %s", (left, bright))
            elif la1 and la1 in [GE]:
                pass
                _t151 = _t
                tmp83_AST_in = _t
                self.match(_t,GE)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t151
                _t = _t.getNextSibling()
                exp = ("%s >= %s", (left, right))
            elif la1 and la1 in [SL]:
                pass
                _t152 = _t
                tmp84_AST_in = _t
                self.match(_t,SL)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t152
                _t = _t.getNextSibling()
                exp = ("%s << %s", (left, right))
            elif la1 and la1 in [SR]:
                pass
                _t153 = _t
                tmp85_AST_in = _t
                self.match(_t,SR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t153
                _t = _t.getNextSibling()
                exp = ("%s >> %s", (left, right))
            elif la1 and la1 in [BSR]:
                pass
                _t154 = _t
                tmp86_AST_in = _t
                self.match(_t,BSR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t154
                _t = _t.getNextSibling()
                raise NotImplementedError("BSR")
            elif la1 and la1 in [PLUS]:
                pass
                _t155 = _t
                tmp87_AST_in = _t
                self.match(_t,PLUS)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t155
                _t = _t.getNextSibling()
                exp = ("%s + %s", (left, right))
            elif la1 and la1 in [MINUS]:
                pass
                _t156 = _t
                tmp88_AST_in = _t
                self.match(_t,MINUS)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t156
                _t = _t.getNextSibling()
                exp = ("%s - %s", (left, right))
            elif la1 and la1 in [DIV]:
                pass
                _t157 = _t
                tmp89_AST_in = _t
                self.match(_t,DIV)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t157
                _t = _t.getNextSibling()
                exp = ("%s / %s", (left, right))
            elif la1 and la1 in [MOD]:
                pass
                _t158 = _t
                tmp90_AST_in = _t
                self.match(_t,MOD)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t158
                _t = _t.getNextSibling()
                exp = ("%s %% %s", (left, right))
            elif la1 and la1 in [STAR]:
                pass
                _t159 = _t
                tmp91_AST_in = _t
                self.match(_t,STAR)
                _t = _t.getFirstChild()
                left=self.expr(_t, block)
                _t = self._retTree
                right=self.expr(_t, block)
                _t = self._retTree
                _t = _t159
                _t = _t.getNextSibling()
                exp = ("%s * %s", (left, right))
            elif la1 and la1 in [INC]:
                pass
                _t160 = _t
                tmp92_AST_in = _t
                self.match(_t,INC)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t160
                _t = _t.getNextSibling()
                exp = ("%s += 1", ex)
            elif la1 and la1 in [DEC]:
                pass
                _t161 = _t
                tmp93_AST_in = _t
                self.match(_t,DEC)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t161
                _t = _t.getNextSibling()
                exp = ("%s -= 1", ex)
            elif la1 and la1 in [POST_INC]:
                pass
                _t162 = _t
                tmp94_AST_in = _t
                self.match(_t,POST_INC)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t162
                _t = _t.getNextSibling()
                exp = ("%s += 1", ex)
            elif la1 and la1 in [POST_DEC]:
                pass
                _t163 = _t
                tmp95_AST_in = _t
                self.match(_t,POST_DEC)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t163
                _t = _t.getNextSibling()
                exp = ("%s -= 1", ex)
            elif la1 and la1 in [BNOT]:
                pass
                _t164 = _t
                tmp96_AST_in = _t
                self.match(_t,BNOT)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t164
                _t = _t.getNextSibling()
                exp = ("~%s", ex)
            elif la1 and la1 in [LNOT]:
                pass
                _t165 = _t
                tmp97_AST_in = _t
                self.match(_t,LNOT)
                _t = _t.getFirstChild()
                ex=self.expr(_t, block)
                _t = self._retTree
                _t = _t165
                _t = _t.getNextSibling()
                exp = ("not %s", ex)
            elif la1 and la1 in [LITERAL_instanceof]:
                pass
                _t166 = _t
                tmp98_AST_in = _t
                self.match(_t,LITERAL_instanceof)
                _t = _t.getFirstChild()
                obj=self.expr(_t, block)
                _t = self._retTree
                typ=self.expr(_t, block)
                _t = self._retTree
                _t = _t166
                _t = _t.getNextSibling()
                exp = ("isinstance(%s, (%s))", (obj, typ))
            elif la1 and la1 in [UNARY_MINUS]:
                pass
                _t167 = _t
                tmp99_AST_in = _t
                self.match(_t,UNARY_MINUS)
                _t = _t.getFirstChild()
                uex=self.expr(_t, block)
                _t = self._retTree
                _t = _t167
                _t = _t.getNextSibling()
                exp = ("-%s", uex)
            elif la1 and la1 in [UNARY_PLUS]:
                pass
                _t168 = _t
                tmp100_AST_in = _t
                self.match(_t,UNARY_PLUS)
                _t = _t.getFirstChild()
                uex=self.expr(_t, block)
                _t = self._retTree
                _t = _t168
                _t = _t.getNextSibling()
                exp = ("+%s", uex)
            elif la1 and la1 in [TYPE,TYPECAST,INDEX_OP,METHOD_CALL,SUPER_CTOR_CALL,CTOR_CALL,IDENT,DOT,LITERAL_this,LITERAL_super,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                pass
                exp=self.primary_expr(_t, block)
                _t = self._retTree
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return exp
    
    def primary_expr(self, _t,
        block
    ):    
        exp = block.missingValue
        
        primary_expr_AST_in = None
        if _t != antlr.ASTNULL:
            primary_expr_AST_in = _t
        i0 = None
        id0 = None
        this0 = None
        class0 = None
        id1 = None
        ar0 = None
        class1 = None
        class2 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [IDENT]:
                pass
                i0 = _t
                self.match(_t,IDENT)
                _t = _t.getNextSibling()
                exp = ("%s", i0.getText())
            elif la1 and la1 in [DOT]:
                pass
                dotexpr = this0 = class0 = class1 = class2 = id0 = id1 = ar0 = typ = None
                index = None
                _t170 = _t
                tmp101_AST_in = _t
                self.match(_t,DOT)
                _t = _t.getFirstChild()
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [TYPE,TYPECAST,INDEX_OP,POST_INC,POST_DEC,METHOD_CALL,UNARY_MINUS,UNARY_PLUS,SUPER_CTOR_CALL,CTOR_CALL,IDENT,DOT,STAR,LITERAL_this,LITERAL_super,ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                    pass
                    dotexpr=self.expr(_t, block)
                    _t = self._retTree
                    if not _t:
                        _t = antlr.ASTNULL
                    la1 = _t.getType()
                    if False:
                        pass
                    elif la1 and la1 in [IDENT]:
                        pass
                        id0 = _t
                        self.match(_t,IDENT)
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [INDEX_OP]:
                        pass
                        index=self.array_index(_t, block)
                        _t = self._retTree
                    elif la1 and la1 in [LITERAL_this]:
                        pass
                        this0 = _t
                        self.match(_t,LITERAL_this)
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [LITERAL_class]:
                        pass
                        class0 = _t
                        self.match(_t,LITERAL_class)
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [LITERAL_new]:
                        pass
                        _t173 = _t
                        tmp102_AST_in = _t
                        self.match(_t,LITERAL_new)
                        _t = _t.getFirstChild()
                        id1 = _t
                        self.match(_t,IDENT)
                        _t = _t.getNextSibling()
                        el0=self.expr_list(_t, block)
                        _t = self._retTree
                        _t = _t173
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [LITERAL_super]:
                        pass
                        tmp103_AST_in = _t
                        self.match(_t,LITERAL_super)
                        _t = _t.getNextSibling()
                    else:
                            raise antlr.NoViableAltException(_t)
                        
                elif la1 and la1 in [ARRAY_DECLARATOR]:
                    pass
                    _t174 = _t
                    ar0 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
                    self.match(_t,ARRAY_DECLARATOR)
                    _t = _t.getFirstChild()
                    self.type_spec_array(_t, block)
                    _t = self._retTree
                    _t = _t174
                    _t = _t.getNextSibling()
                    if not _t:
                        _t = antlr.ASTNULL
                    la1 = _t.getType()
                    if False:
                        pass
                    elif la1 and la1 in [LITERAL_class]:
                        pass
                        class1 = _t
                        self.match(_t,LITERAL_class)
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [3]:
                        pass
                    else:
                            raise antlr.NoViableAltException(_t)
                        
                elif la1 and la1 in [LITERAL_void,LITERAL_boolean,LITERAL_byte,LITERAL_char,LITERAL_short,LITERAL_int,LITERAL_float,LITERAL_long,LITERAL_double]:
                    pass
                    typ=self.builtin_type(_t, block)
                    _t = self._retTree
                    if not _t:
                        _t = antlr.ASTNULL
                    la1 = _t.getType()
                    if False:
                        pass
                    elif la1 and la1 in [LITERAL_class]:
                        pass
                        class2 = _t
                        self.match(_t,LITERAL_class)
                        _t = _t.getNextSibling()
                    elif la1 and la1 in [3]:
                        pass
                    else:
                            raise antlr.NoViableAltException(_t)
                        
                else:
                        raise antlr.NoViableAltException(_t)
                    
                _t = _t170
                _t = _t.getNextSibling()
                if id0:
                   exp = ("%s.%s", (dotexpr, ("%s", id0.getText())))
                elif ar0:
                   exp = ("%s", "[]")
                   if class1:
                       exp = ("%s.__class__", exp)
                elif typ:
                   exp = ("%s", typ)
                elif id1:
                   el0 = el0 or ""
                   exp = ("%s(%s)", (("%s", id1.getText()), ("%s", el0)))
            elif la1 and la1 in [INDEX_OP]:
                pass
                index=self.array_index(_t, block)
                _t = self._retTree
                exp = index
            elif la1 and la1 in [METHOD_CALL]:
                pass
                _t177 = _t
                tmp104_AST_in = _t
                self.match(_t,METHOD_CALL)
                _t = _t.getFirstChild()
                pex=self.primary_expr(_t, block)
                _t = self._retTree
                exl=self.expr_list(_t, block)
                _t = self._retTree
                _t = _t177
                _t = _t.getNextSibling()
                if exl:
                   exp = ("%s(%s)", (pex, exl))
                else:
                   exp = ("%s()", pex)
            elif la1 and la1 in [SUPER_CTOR_CALL,CTOR_CALL]:
                pass
                self.ctor_call(_t, block)
                _t = self._retTree
            elif la1 and la1 in [TYPECAST]:
                pass
                _t178 = _t
                tmp105_AST_in = _t
                self.match(_t,TYPECAST)
                _t = _t.getFirstChild()
                type_cast=self.type_spec(_t, block)
                _t = self._retTree
                cast_exp=self.expr(_t, block)
                _t = self._retTree
                _t = _t178
                _t = _t.getNextSibling()
                exp = ("%s", cast_exp)
            elif la1 and la1 in [LITERAL_new]:
                pass
                other_exp=self.new_expression(_t, block)
                _t = self._retTree
                exp = ("%s", other_exp)
            elif la1 and la1 in [NUM_INT,CHAR_LITERAL,STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE]:
                pass
                con=self.constant(_t, block)
                _t = self._retTree
                exp = ("%s", con)
            elif la1 and la1 in [LITERAL_super]:
                pass
                tmp106_AST_in = _t
                self.match(_t,LITERAL_super)
                _t = _t.getNextSibling()
                exp = ("%s", "super"  )
            elif la1 and la1 in [LITERAL_true]:
                pass
                tmp107_AST_in = _t
                self.match(_t,LITERAL_true)
                _t = _t.getNextSibling()
                exp = ("%s", "True"   )
            elif la1 and la1 in [LITERAL_false]:
                pass
                tmp108_AST_in = _t
                self.match(_t,LITERAL_false)
                _t = _t.getNextSibling()
                exp = ("%s", "False"  )
            elif la1 and la1 in [LITERAL_this]:
                pass
                tmp109_AST_in = _t
                self.match(_t,LITERAL_this)
                _t = _t.getNextSibling()
                exp = ("%s", "self"   )
            elif la1 and la1 in [LITERAL_null]:
                pass
                tmp110_AST_in = _t
                self.match(_t,LITERAL_null)
                _t = _t.getNextSibling()
                exp = ("%s", "None"   )
            elif la1 and la1 in [TYPE]:
                pass
                typ=self.type_spec(_t, block)
                _t = self._retTree
                exp = ("%s", typ)
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return exp
    
    def array_index(self, _t,
        block
    ):    
        index = None
        
        array_index_AST_in = None
        if _t != antlr.ASTNULL:
            array_index_AST_in = _t
        try:      ## for error handling
            pass
            _t184 = _t
            tmp111_AST_in = _t
            self.match(_t,INDEX_OP)
            _t = _t.getFirstChild()
            array_exp=self.expr(_t, block)
            _t = self._retTree
            index_exp=self.expression(_t, block, False)
            _t = self._retTree
            _t = _t184
            _t = _t.getNextSibling()
            index = ("%s[%s]", (array_exp, index_exp))
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return index
    
    def new_expression(self, _t,
        block
    ):    
        value = block.missingValue
        
        new_expression_AST_in = None
        if _t != antlr.ASTNULL:
            new_expression_AST_in = _t
        exp = ()
        arrexp = None
        arrdecl = None
        try:      ## for error handling
            pass
            _t187 = _t
            tmp112_AST_in = _t
            self.match(_t,LITERAL_new)
            _t = _t.getFirstChild()
            typ=self.type(_t, block)
            _t = self._retTree
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [ARRAY_DECLARATOR]:
                pass
                arrdecl=self.new_array_declarator(_t, block)
                _t = self._retTree
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [ARRAY_INIT]:
                    pass
                    arrexp=self.array_initializer(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
            elif la1 and la1 in [ELIST]:
                pass
                exp=self.expr_list(_t, block)
                _t = self._retTree
                if not _t:
                    _t = antlr.ASTNULL
                la1 = _t.getType()
                if False:
                    pass
                elif la1 and la1 in [OBJBLOCK]:
                    pass
                    self.obj_block(_t, block)
                    _t = self._retTree
                elif la1 and la1 in [3]:
                    pass
                else:
                        raise antlr.NoViableAltException(_t)
                    
            else:
                    raise antlr.NoViableAltException(_t)
                
            _t = _t187
            _t = _t.getNextSibling()
            alltypes = block.config.combined("typeValueMap")
            if arrdecl:
               value = ("[%s() for __idx0 in range(%s)]", (("%s", typ), ("%s", arrdecl)))
            elif exp:
               value = ("%s(%s)", (("%s", typ), ("%s", exp)))
            elif typ in alltypes:
               value = ("%s", alltypes[typ])
            else:
               value = ("%s()", typ)
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return value
    
    def constant(self, _t,
        block
    ):    
        value = None
        
        constant_AST_in = None
        if _t != antlr.ASTNULL:
            constant_AST_in = _t
        i0 = None
        c0 = None
        s0 = None
        f0 = None
        d0 = None
        l0 = None
        try:      ## for error handling
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [NUM_INT]:
                pass
                i0 = _t
                self.match(_t,NUM_INT)
                _t = _t.getNextSibling()
                value = i0.getText()
            elif la1 and la1 in [CHAR_LITERAL]:
                pass
                c0 = _t
                self.match(_t,CHAR_LITERAL)
                _t = _t.getNextSibling()
                value = c0.getText()
            elif la1 and la1 in [STRING_LITERAL]:
                pass
                s0 = _t
                self.match(_t,STRING_LITERAL)
                _t = _t.getNextSibling()
                value = s0.getText()
            elif la1 and la1 in [NUM_FLOAT]:
                pass
                f0 = _t
                self.match(_t,NUM_FLOAT)
                _t = _t.getNextSibling()
                value = f0.getText()
            elif la1 and la1 in [NUM_DOUBLE]:
                pass
                d0 = _t
                self.match(_t,NUM_DOUBLE)
                _t = _t.getNextSibling()
                value = d0.getText()
            elif la1 and la1 in [NUM_LONG]:
                pass
                l0 = _t
                self.match(_t,NUM_LONG)
                _t = _t.getNextSibling()
                value = l0.getText()
            else:
                    raise antlr.NoViableAltException(_t)
                
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return value
    
    def new_array_declarator(self, _t,
        block
    ):    
        exp = None
        
        new_array_declarator_AST_in = None
        if _t != antlr.ASTNULL:
            new_array_declarator_AST_in = _t
        ad0 = None
        try:      ## for error handling
            pass
            _t192 = _t
            ad0 = antlr.ifelse(_t == antlr.ASTNULL, None, _t)
            self.match(_t,ARRAY_DECLARATOR)
            _t = _t.getFirstChild()
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [ARRAY_DECLARATOR]:
                pass
                exp=self.new_array_declarator(_t, block)
                _t = self._retTree
            elif la1 and la1 in [3,EXPR]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            if not _t:
                _t = antlr.ASTNULL
            la1 = _t.getType()
            if False:
                pass
            elif la1 and la1 in [EXPR]:
                pass
                exp=self.expression(_t, block, False)
                _t = self._retTree
            elif la1 and la1 in [3]:
                pass
            else:
                    raise antlr.NoViableAltException(_t)
                
            _t = _t192
            _t = _t.getNextSibling()
        
        except antlr.RecognitionException, ex:
            self.reportError(ex)
            if _t:
                _t = _t.getNextSibling()
        
        self._retTree = _t
        return exp
    

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
    "FLOAT_SUFFIX", 
    "\"const\""
]
    

### generate bit set
def mk_tokenSet_0(): 
    ### var1
    data = [ -2305839160922996736L, 63L, 8388608L, 0L, 0L, 0L]
    return data
_tokenSet_0 = antlr.BitSet(mk_tokenSet_0())

### generate bit set
def mk_tokenSet_1(): 
    ### var1
    data = [ 13469290120320L, 5365563392L, 0L, 0L]
    return data
_tokenSet_1 = antlr.BitSet(mk_tokenSet_1())
