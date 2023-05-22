import { test, expect, Locator } from '@playwright/test';

const url = "/";

test('Page loads successfully', async ({ page }) => {
  await page.goto(url);

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Safe Contract Changes Demo/);
});

const repeatCount = Number.parseInt(process.env.REPEAT_COUNT || "1");

const testSingleName = async (page, idx) => {
  const nameInput = page.getByLabel("Name");
  await expect(nameInput).toBeEnabled();

  const name = `Demo Name ${idx}`;

  await nameInput.fill(name);
  const addPersonButton = await page.getByRole('button', {name: "Add Person"});
  await expect(addPersonButton).toBeEnabled();

  await addPersonButton.click();

  const nameConfirm = await page.getByText(name);
  await expect(nameConfirm).toBeVisible();
}

const testMultipleNames = async (page, idx) => {
  const firstNameInput = page.getByLabel("Firstname");
  await expect(firstNameInput).toBeEnabled();
  const lastNameInput = page.getByLabel("Lastname");
  await expect(lastNameInput).toBeEnabled();

  const firstName = `Firstname ${idx}`;
  const lastName = `Lastname ${idx}`;

  await firstNameInput.fill(firstName);
  await lastNameInput.fill(lastName);

  const addPersonButton = await page.getByRole('button', {name: "Add Person"});
  await expect(addPersonButton).toBeEnabled();

  await addPersonButton.click();

  const nameConfirm = await page.getByText(`${firstName} ${lastName}`);
  await expect(nameConfirm).toBeVisible();
}

const indexOfEnabled = async (locators: Locator[]): Promise<number> => 
  await Promise.race(locators.map((l, idx) => l.isEnabled().then(() => idx)));


test(`Can add a new person ${repeatCount} times`, async ({ page }) => { 
    page.goto(url);

    for (var idx = 1; idx <= repeatCount; idx++) {

      const nameInputField = page.getByLabel('Name', {exact: true});
      const firstNameInputField = page.getByLabel('Firstname', {exact: true});
      const idx = await indexOfEnabled([nameInputField, firstNameInputField]);

      const isSingleInputVersion = idx == 0;
      console.log(`idx = ${idx}, isSingleInputVersion: ${isSingleInputVersion}`);
      
      if (isSingleInputVersion) {
        console.log('using single names version');
        await testSingleName(page, idx);
      } else {
        console.log('using multiple names version');
        await testMultipleNames(page, idx);
      }
    }
}); 