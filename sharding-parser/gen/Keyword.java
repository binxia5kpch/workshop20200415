// Generated from /Users/kangpengcheng1/IdeaProjects/workshop20200415/sharding-parser/src/main/antlr4/imports/Keyword.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Keyword extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, USE=2, INSERT=3, SELECT=4, DELETE=5, UPDATE=6, SET=7, TABLE=8, COLUMN=9, 
		INTO=10, VALUES=11, VALUE=12, FROM=13, WHERE=14, AND=15, OR=16, NOT=17, 
		BETWEEN=18, IN=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "USE", "INSERT", "SELECT", "DELETE", "UPDATE", "SET", "TABLE", 
			"COLUMN", "INTO", "VALUES", "VALUE", "FROM", "WHERE", "AND", "OR", "NOT", 
			"BETWEEN", "IN", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", 
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", 
			"Z", "UL_"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "USE", "INSERT", "SELECT", "DELETE", "UPDATE", "SET", "TABLE", 
			"COLUMN", "INTO", "VALUES", "VALUE", "FROM", "WHERE", "AND", "OR", "NOT", 
			"BETWEEN", "IN"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public Keyword(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Keyword.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\25\u0100\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\3\2\6\2a\n\2\r\2\16\2b\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3"+
		"#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3."+
		"\3/\3/\2\2\60\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2"+
		";\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2\3\2\35\5\2\13\f"+
		"\17\17\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2"+
		"JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4"+
		"\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{"+
		"{\4\2\\\\||\2\u00e5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3`\3\2\2\2\5f\3\2\2\2"+
		"\7j\3\2\2\2\tq\3\2\2\2\13x\3\2\2\2\r\177\3\2\2\2\17\u0086\3\2\2\2\21\u008a"+
		"\3\2\2\2\23\u0090\3\2\2\2\25\u0097\3\2\2\2\27\u009c\3\2\2\2\31\u00a3\3"+
		"\2\2\2\33\u00a9\3\2\2\2\35\u00ae\3\2\2\2\37\u00b4\3\2\2\2!\u00b8\3\2\2"+
		"\2#\u00bb\3\2\2\2%\u00bf\3\2\2\2\'\u00c7\3\2\2\2)\u00ca\3\2\2\2+\u00cc"+
		"\3\2\2\2-\u00ce\3\2\2\2/\u00d0\3\2\2\2\61\u00d2\3\2\2\2\63\u00d4\3\2\2"+
		"\2\65\u00d6\3\2\2\2\67\u00d8\3\2\2\29\u00da\3\2\2\2;\u00dc\3\2\2\2=\u00de"+
		"\3\2\2\2?\u00e0\3\2\2\2A\u00e2\3\2\2\2C\u00e4\3\2\2\2E\u00e6\3\2\2\2G"+
		"\u00e8\3\2\2\2I\u00ea\3\2\2\2K\u00ec\3\2\2\2M\u00ee\3\2\2\2O\u00f0\3\2"+
		"\2\2Q\u00f2\3\2\2\2S\u00f4\3\2\2\2U\u00f6\3\2\2\2W\u00f8\3\2\2\2Y\u00fa"+
		"\3\2\2\2[\u00fc\3\2\2\2]\u00fe\3\2\2\2_a\t\2\2\2`_\3\2\2\2ab\3\2\2\2b"+
		"`\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\b\2\2\2e\4\3\2\2\2fg\5Q)\2gh\5M\'\2hi"+
		"\5\61\31\2i\6\3\2\2\2jk\59\35\2kl\5C\"\2lm\5M\'\2mn\5\61\31\2no\5K&\2"+
		"op\5O(\2p\b\3\2\2\2qr\5M\'\2rs\5\61\31\2st\5? \2tu\5\61\31\2uv\5-\27\2"+
		"vw\5O(\2w\n\3\2\2\2xy\5/\30\2yz\5\61\31\2z{\5? \2{|\5\61\31\2|}\5O(\2"+
		"}~\5\61\31\2~\f\3\2\2\2\177\u0080\5Q)\2\u0080\u0081\5G$\2\u0081\u0082"+
		"\5/\30\2\u0082\u0083\5)\25\2\u0083\u0084\5O(\2\u0084\u0085\5\61\31\2\u0085"+
		"\16\3\2\2\2\u0086\u0087\5M\'\2\u0087\u0088\5\61\31\2\u0088\u0089\5O(\2"+
		"\u0089\20\3\2\2\2\u008a\u008b\5O(\2\u008b\u008c\5)\25\2\u008c\u008d\5"+
		"+\26\2\u008d\u008e\5? \2\u008e\u008f\5\61\31\2\u008f\22\3\2\2\2\u0090"+
		"\u0091\5-\27\2\u0091\u0092\5E#\2\u0092\u0093\5? \2\u0093\u0094\5Q)\2\u0094"+
		"\u0095\5A!\2\u0095\u0096\5C\"\2\u0096\24\3\2\2\2\u0097\u0098\59\35\2\u0098"+
		"\u0099\5C\"\2\u0099\u009a\5O(\2\u009a\u009b\5E#\2\u009b\26\3\2\2\2\u009c"+
		"\u009d\5S*\2\u009d\u009e\5)\25\2\u009e\u009f\5? \2\u009f\u00a0\5Q)\2\u00a0"+
		"\u00a1\5\61\31\2\u00a1\u00a2\5M\'\2\u00a2\30\3\2\2\2\u00a3\u00a4\5S*\2"+
		"\u00a4\u00a5\5)\25\2\u00a5\u00a6\5? \2\u00a6\u00a7\5Q)\2\u00a7\u00a8\5"+
		"\61\31\2\u00a8\32\3\2\2\2\u00a9\u00aa\5\63\32\2\u00aa\u00ab\5K&\2\u00ab"+
		"\u00ac\5E#\2\u00ac\u00ad\5A!\2\u00ad\34\3\2\2\2\u00ae\u00af\5U+\2\u00af"+
		"\u00b0\5\67\34\2\u00b0\u00b1\5\61\31\2\u00b1\u00b2\5K&\2\u00b2\u00b3\5"+
		"\61\31\2\u00b3\36\3\2\2\2\u00b4\u00b5\5)\25\2\u00b5\u00b6\5C\"\2\u00b6"+
		"\u00b7\5/\30\2\u00b7 \3\2\2\2\u00b8\u00b9\5E#\2\u00b9\u00ba\5K&\2\u00ba"+
		"\"\3\2\2\2\u00bb\u00bc\5C\"\2\u00bc\u00bd\5E#\2\u00bd\u00be\5O(\2\u00be"+
		"$\3\2\2\2\u00bf\u00c0\5+\26\2\u00c0\u00c1\5\61\31\2\u00c1\u00c2\5O(\2"+
		"\u00c2\u00c3\5U+\2\u00c3\u00c4\5\61\31\2\u00c4\u00c5\5\61\31\2\u00c5\u00c6"+
		"\5C\"\2\u00c6&\3\2\2\2\u00c7\u00c8\59\35\2\u00c8\u00c9\5C\"\2\u00c9(\3"+
		"\2\2\2\u00ca\u00cb\t\3\2\2\u00cb*\3\2\2\2\u00cc\u00cd\t\4\2\2\u00cd,\3"+
		"\2\2\2\u00ce\u00cf\t\5\2\2\u00cf.\3\2\2\2\u00d0\u00d1\t\6\2\2\u00d1\60"+
		"\3\2\2\2\u00d2\u00d3\t\7\2\2\u00d3\62\3\2\2\2\u00d4\u00d5\t\b\2\2\u00d5"+
		"\64\3\2\2\2\u00d6\u00d7\t\t\2\2\u00d7\66\3\2\2\2\u00d8\u00d9\t\n\2\2\u00d9"+
		"8\3\2\2\2\u00da\u00db\t\13\2\2\u00db:\3\2\2\2\u00dc\u00dd\t\f\2\2\u00dd"+
		"<\3\2\2\2\u00de\u00df\t\r\2\2\u00df>\3\2\2\2\u00e0\u00e1\t\16\2\2\u00e1"+
		"@\3\2\2\2\u00e2\u00e3\t\17\2\2\u00e3B\3\2\2\2\u00e4\u00e5\t\20\2\2\u00e5"+
		"D\3\2\2\2\u00e6\u00e7\t\21\2\2\u00e7F\3\2\2\2\u00e8\u00e9\t\22\2\2\u00e9"+
		"H\3\2\2\2\u00ea\u00eb\t\23\2\2\u00ebJ\3\2\2\2\u00ec\u00ed\t\24\2\2\u00ed"+
		"L\3\2\2\2\u00ee\u00ef\t\25\2\2\u00efN\3\2\2\2\u00f0\u00f1\t\26\2\2\u00f1"+
		"P\3\2\2\2\u00f2\u00f3\t\27\2\2\u00f3R\3\2\2\2\u00f4\u00f5\t\30\2\2\u00f5"+
		"T\3\2\2\2\u00f6\u00f7\t\31\2\2\u00f7V\3\2\2\2\u00f8\u00f9\t\32\2\2\u00f9"+
		"X\3\2\2\2\u00fa\u00fb\t\33\2\2\u00fbZ\3\2\2\2\u00fc\u00fd\t\34\2\2\u00fd"+
		"\\\3\2\2\2\u00fe\u00ff\7a\2\2\u00ff^\3\2\2\2\4\2b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}