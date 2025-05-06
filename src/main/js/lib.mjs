
const {z} = require('zod');

const PersonSchema = z.object({
    firstname: z.string().min(3).max(10),
    lastname: z.string().min(3).max(10),
});

Polyglot.eval('python', `print("hallo from python")`);

export function validate(input) {
    const result = PersonSchema.safeParse(input);
    return result.error?.errors ?? [];
}
