package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;

/**
 * Finds and lists all flashcards in the flashcard app whose English phrase or foreign phrase 
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_DESCRIPTION = "Finds flashcard(s) that have a matching phrase";
    public static final String COMMAND_USAGE = "find PHRASE";
    public static final String COMMAND_EXAMPLES = "find Good Morning";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all flashcards whose english or foreign phrases contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " hello";

    private final PhraseContainsKeywordsPredicate predicate;

    public FindCommand(PhraseContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW, model.getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
