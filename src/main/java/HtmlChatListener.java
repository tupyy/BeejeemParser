public class HtmlChatListener extends JobfileBaseListener {
    public void HtmlChatListener() {}

    @Override
    public void enterLine(JobfileParser.LineContext ctx) {
        super.enterLine(ctx);
        if (ctx.statement() != null) {
            System.out.println("Statement");
        }
    }

    @Override
    public void exitLine(JobfileParser.LineContext ctx) {
        super.exitLine(ctx);
    }

    @Override
    public void enterVarstmt(JobfileParser.VarstmtContext ctx) {
        super.enterVarstmt(ctx);
    }

    @Override
    public void exitVarstmt(JobfileParser.VarstmtContext ctx) {
        super.exitVarstmt(ctx);
    }

}
