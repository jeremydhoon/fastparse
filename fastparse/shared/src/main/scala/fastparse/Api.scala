package fastparse
import language.experimental.macros
import fastparse.parsers.Intrinsics
import acyclic.file
trait pkg{
  implicit def enclosingFunctionName: Utils.FuncName = macro Utils.FuncName.impl

  val Result = core.Result

  val Pass = parsers.Terminals.Pass
  val Fail = parsers.Terminals.Fail
  val Start = parsers.Terminals.Start
  val End = parsers.Terminals.End
  val Index = parsers.Terminals.Index
  val AnyChar = parsers.Terminals.AnyChar
  val IgnoreCase = parsers.Terminals.IgnoreCase

  val CharPred = Intrinsics.CharPred
  val CharIn = Intrinsics.CharIn
  val CharsWhile = Intrinsics.CharsWhile
  val CharPredicates = fastparse.CharPredicates
  val StringIn = Intrinsics.StringIn
  val NoTrace = parsers.Combinators.NoTrace
  val & = parsers.Combinators.Lookahead

  implicit def wspStr(s: String): P0 =
    if (s.length == 1) parsers.Terminals.CharLiteral(s(0))
    else parsers.Terminals.Literal(s)
  import core.Parser
  def P[T](p: => Parser[T])(implicit name: Utils.FuncName): Parser[T] =
    parsers.Combinators.Rule(name.name, () => p)

  type P0 = Parser[Unit]

  type P[+T] = Parser[T]
}
object all extends pkg{
  implicit def parserApi[T, V](p: T)(implicit c: T => core.Parser[V]): ParserApi[V] =
    new ParserApiImpl[V](p)
}
object noApi extends pkg