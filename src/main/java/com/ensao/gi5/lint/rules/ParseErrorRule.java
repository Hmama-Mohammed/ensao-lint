package com.ensao.gi5.lint.rules;

import com.ensao.gi5.lint.constantes.Constantes;
import com.ensao.gi5.lint.rules.violations.Violation;
import com.ensao.gi5.lint.rules.violations.ViolationFactory;
import com.ensao.gi5.lint.wrapper.CompilationUnitWrapper;
import com.github.javaparser.JavaToken;
import com.github.javaparser.Problem;
import com.github.javaparser.TokenRange;

public class ParseErrorRule extends Rule {

    public ParseErrorRule() {
        super(Constantes.LINT_REG_000, Level.BLOCKER);
    }

    @Override
    public void apply(CompilationUnitWrapper compilationUnit) {

        for (Problem problem : compilationUnit.getProblems()) {

            int line = problem.getLocation()
                    .map(TokenRange::getBegin)
                    .flatMap(JavaToken::getRange)
                    .map(range -> range.begin)
                    .map(position -> position.line)
                    .orElse(-1);

            final Violation violation = ViolationFactory.ViolationFactory(
                    compilationUnit.getFileName(),
                    problem.getMessage(),
                    line);
            violation.setDescription(problem.getMessage());
            addViolation(violation);
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
