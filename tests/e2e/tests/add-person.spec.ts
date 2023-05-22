import { test, expect } from '@playwright/test';

const url = "/";

test('Page loads successfully', async ({ page }) => {
  await page.goto(url);

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Safe Contract Changes Demo/);
});

const repeatCount = Number.parseInt(process.env.REPEAT_COUNT || "1");


test(`Can add a new person ${repeatCount} times`, async ({ page }) => { 
    page.goto(url);
    
    for (var idx = 1; idx <= repeatCount; idx++) {

      const nameInput = await page.getByLabel('Name');
      await expect(nameInput).toBeEnabled();

      const name = `Demo Name ${idx}`;

      await nameInput.fill(name);
      const addPersonButton = await page.getByRole('button', {name: "Add Person"});
      await expect(addPersonButton).toBeEnabled();

      await addPersonButton.click();

      const nameConfirm = await page.getByText(name);
      await expect(nameConfirm).toBeVisible();
    }
}); 

